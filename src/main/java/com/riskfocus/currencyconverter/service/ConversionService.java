package com.riskfocus.currencyconverter.service;

import com.google.common.graph.ImmutableValueGraph.Builder;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import com.riskfocus.currencyconverter.model.dto.CurrencyConversionRateDto;
import com.riskfocus.currencyconverter.model.dto.CurrencyConversionRequest;
import com.riskfocus.currencyconverter.model.dto.UpdateConversionRateRequest;
import com.riskfocus.currencyconverter.model.repository.CurrencyConversionRateRepository;
import com.riskfocus.currencyconverter.service.dijkstra.ShortestPathCalculator;
import com.riskfocus.currencyconverter.validation.ConversionNotSupported;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.RoundingMode.HALF_UP;

@SuppressWarnings("ALL")
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionService {

    private final CurrencyService currencyService;
    private final CurrencyConversionRateRepository conversionRateRepository;
    private final ShortestPathCalculator pathCalculator;

    @Transactional(readOnly = true)
    public BigDecimal convert(CurrencyConversionRequest request) {
        Currency fromCurrenct = currencyService.getByCodeOrThrow(request.getFrom());
        Currency toCurrency = currencyService.getByCodeOrThrow(request.getTo());
        return Optional.ofNullable(pathCalculator.getBestConversionRate(createCurrencyConversionGraph(), fromCurrenct, toCurrency))
                .map(request.getAmount()::multiply)
                .map(a -> a.setScale(2, HALF_UP))
                .orElseThrow(ConversionNotSupported::new);
    }

    @Transactional
    public CurrencyConversionRateDto updateRate(UpdateConversionRateRequest request) {
        log.info("Setting conversion rate `{}`->`{}` to {}", request.getFrom(), request.getTo(), request.getRate());

        CurrencyConversionRate currencyConversionRate =
                Optional.ofNullable(conversionRateRepository.findCurrencyConversionRate(request.getFrom(), request.getTo()))
                        .orElseGet(() -> createNewEntity(request));

        currencyConversionRate.setRate(request.getRate());

        return Optional.of(currencyConversionRate)
                .map(conversionRateRepository::save)
                .map(CurrencyConversionRateDto::fromEntity)
                .orElseThrow(IllegalStateException::new);
    }

    private CurrencyConversionRate createNewEntity(UpdateConversionRateRequest request) {
        return CurrencyConversionRate.builder()
                .from(currencyService.getByCodeOrThrow(request.getFrom()))
                .to(currencyService.getByCodeOrThrow(request.getTo()))
                .build();
    }

    // Add cache + manual cache eviciton on currency update
    private ValueGraph createCurrencyConversionGraph() {
        Builder<Currency, BigDecimal> graph = ValueGraphBuilder.directed().immutable();
        conversionRateRepository.findAll().stream()
                .forEach(c -> graph.putEdgeValue(c.getFrom(), c.getTo(), c.getRate()));
        return graph.build();
    }
}

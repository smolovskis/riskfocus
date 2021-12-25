package com.riskfocus.currencyconverter.service;

import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import com.riskfocus.currencyconverter.model.repository.CurrencyConversionRateRepository;
import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyConversionRateRepository conversionRateRepository;

    @Transactional(readOnly = true)
    public BigDecimal convert(String from, String to, BigDecimal amount) {
        return conversionRateRepository.findCurrencyConversionRateByFromCodeAndToCode(from, to)
                .map(CurrencyConversionRate::getRate)
                .map(amount::multiply)
                .orElseThrow();
    }

    @Transactional
    public CurrencyConversionRate updateRate(String from, String to, BigDecimal rate) {
        CurrencyConversionRate currencyConversionRate = conversionRateRepository.findCurrencyConversionRateByFromCodeAndToCode(from, to)
                .or()
                .ifPresentOrElse(CurrencyConversionRate::setRate, () -> createNewEntity(from, to, rate));
        return conversionRateRepository.save(currencyConversionRate);
    }

    private void createNewEntity(String from, String to, BigDecimal rate) {
        return conversionRateRepository.save(currencyConversionRate);
    }
}

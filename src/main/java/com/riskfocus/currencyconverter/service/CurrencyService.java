package com.riskfocus.currencyconverter.service;

import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.dto.CurrencyDto;
import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import com.riskfocus.currencyconverter.validation.CurrencyNotSupported;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository repository;

    @Transactional(readOnly = true)
    public List<CurrencyDto> getSupportedCurrencies() {
        return repository.findAll().stream()
                .map(CurrencyDto::fromEntity)
                .collect(toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public Currency getByCodeOrThrow(String code) {
        return repository.findCurrencyByCode(code)
                .orElseThrow(CurrencyNotSupported::new);
    }

    @Transactional
    public CurrencyDto addCurrency(String code) {
        return Optional.of(code)
                .map(c -> Currency.builder()
                        .code(c)
                        .build())
                .map(repository::save)
                .map(CurrencyDto::fromEntity)
                .orElseThrow(IllegalStateException::new);
    }

    @Transactional
    public void deleteCurrency(Long id) {
        repository.deleteById(id);
    }
}

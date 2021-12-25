package com.riskfocus.currencyconverter.web;

import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyRepository repository;


    @GetMapping
    public List<Currency> getSupportedCurrencies() {
        return repository.findAll();
    }

    @PostMapping
    public void add(String code) {
        repository.save(Currency.builder()
                .code(code)
                .build());
    }

    @DeleteMapping
    public void delete(String code) {
        repository.delete(Currency.builder()
                .code(code)
                .build());
    }
}

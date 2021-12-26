package com.riskfocus.currencyconverter.web;

import com.riskfocus.currencyconverter.model.dto.CurrencyDto;
import com.riskfocus.currencyconverter.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/{code}")
    public CurrencyDto add(@PathVariable String code) {
        return currencyService.addCurrency(code);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
    }

    @GetMapping
    public List<CurrencyDto> getSupportedCurrencies() {
        return currencyService.getSupportedCurrencies();
    }
}

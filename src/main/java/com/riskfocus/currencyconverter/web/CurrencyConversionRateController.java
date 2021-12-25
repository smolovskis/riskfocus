package com.riskfocus.currencyconverter.web;

import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import com.riskfocus.currencyconverter.service.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/currency-conversion-rate")
@RequiredArgsConstructor
public class CurrencyConversionRateController {

    private final ConversionService service;

    @PostMapping("convert")
    public void convertCurrency(String from, String to, BigDecimal amount) {
        service.convert(from, to, amount);
    }

    @PostMapping("add")
    public CurrencyConversionRate updateRate(String from, String to, BigDecimal rate) {
        return service.updateRate(from, to, rate);
    }
}

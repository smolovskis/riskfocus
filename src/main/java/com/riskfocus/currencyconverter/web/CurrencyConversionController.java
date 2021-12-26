package com.riskfocus.currencyconverter.web;

import com.riskfocus.currencyconverter.model.dto.CurrencyConversionRateDto;
import com.riskfocus.currencyconverter.model.dto.CurrencyConversionRequest;
import com.riskfocus.currencyconverter.model.dto.UpdateConversionRateRequest;
import com.riskfocus.currencyconverter.service.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/currency-conversion")
@RequiredArgsConstructor
public class CurrencyConversionController {

    private final ConversionService service;

    @PostMapping("convert")
    public BigDecimal convertCurrency(@Validated @RequestBody CurrencyConversionRequest request) {
        return service.convert(request);
    }

    @PostMapping("add")
    public CurrencyConversionRateDto updateRate(@Validated @RequestBody UpdateConversionRateRequest request) {
        return service.updateRate(request);
    }
}

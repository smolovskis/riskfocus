package com.riskfocus.currencyconverter.validation;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class CurrencyNotSupported extends RuntimeException {

    public CurrencyNotSupported() {
        super("Currency not supported");
    }
}

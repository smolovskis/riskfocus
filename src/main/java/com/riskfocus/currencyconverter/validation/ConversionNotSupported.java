package com.riskfocus.currencyconverter.validation;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class ConversionNotSupported extends RuntimeException {

    public ConversionNotSupported() {
        super("Currency not supported");
    }
}

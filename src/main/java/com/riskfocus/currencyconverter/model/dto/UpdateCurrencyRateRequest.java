package com.riskfocus.currencyconverter.model.dto;

import com.riskfocus.currencyconverter.model.Currency;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UpdateCurrencyRateRequest {

    @NotNull
    private String from;

    @NotNull
    @SupportedCurrency
    private String to;

    @NotNull
    @Digits(integer = 19, fraction = 14)
    @Column(name = "RATE", nullable = false)
    private BigDecimal rate;
}

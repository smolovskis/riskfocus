package com.riskfocus.currencyconverter.model.dto;

import com.riskfocus.currencyconverter.validation.SupportedCurrency;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
public class UpdateConversionRateRequest {

    @NotNull
    @SupportedCurrency
    private String from;

    @NotNull
    @SupportedCurrency
    private String to;

    @NotNull
    @Positive
    @Digits(integer = 19, fraction = 14)
    private BigDecimal rate;
}

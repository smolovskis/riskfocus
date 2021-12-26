package com.riskfocus.currencyconverter.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class CurrencyConversionRequest {

    @NotNull
    private final String from;
    @NotNull
    private final String to;
    @NotNull
    private final BigDecimal amount;
}

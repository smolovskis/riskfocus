package com.riskfocus.currencyconverter.service.dijkstra;

import com.riskfocus.currencyconverter.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;

@Data
@AllArgsConstructor
class CumulativeConversionRateHolder implements Comparable<CumulativeConversionRateHolder> {

    private final Currency node;
    private BigDecimal totalConversionRate = ONE;
    private CumulativeConversionRateHolder predecessor;

    public CumulativeConversionRateHolder(Currency node) {
        this.node = node;
    }

    @Override
    public int compareTo(CumulativeConversionRateHolder other) {
        return this.totalConversionRate.compareTo(other.totalConversionRate);
    }
}

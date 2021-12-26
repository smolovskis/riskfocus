package com.riskfocus.currencyconverter.model.dto;

import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRateDto {

    private Long id;
    private Currency from;
    private Currency to;
    private BigDecimal rate;

    public static CurrencyConversionRateDto fromEntity(CurrencyConversionRate entity) {
        return Optional.ofNullable(entity)
                .map(e -> CurrencyConversionRateDto.builder()
                        .from(entity.getFrom())
                        .id(entity.getId())
                        .to(entity.getTo())
                        .rate(entity.getRate())
                        .build())
                .orElseGet(CurrencyConversionRateDto::new);
    }
}

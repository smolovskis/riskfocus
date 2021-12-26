package com.riskfocus.currencyconverter.model.dto;

import com.riskfocus.currencyconverter.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    private Long id;
    private String code;

    public static CurrencyDto fromEntity(Currency entity) {
        return Optional.ofNullable(entity)
                .map(e -> new CurrencyDto(e.getId(), e.getCode()))
                .orElseGet(CurrencyDto::new);
    }
}

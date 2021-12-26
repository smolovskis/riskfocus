package com.riskfocus.currencyconverter.model.repository.custom;

import com.riskfocus.currencyconverter.model.CurrencyConversionRate;

public interface CurrencyConversionRateRepositoryCustom {

    CurrencyConversionRate findCurrencyConversionRate(String fromCode, String toCode);
}

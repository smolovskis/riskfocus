package com.riskfocus.currencyconverter.model.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import lombok.RequiredArgsConstructor;

import static com.riskfocus.currencyconverter.model.QCurrencyConversionRate.currencyConversionRate;

@RequiredArgsConstructor
public class CurrencyConversionRateRepositoryCustomImpl implements CurrencyConversionRateRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CurrencyConversionRate findCurrencyConversionRate(String fromCode, String toCode) {
        return queryFactory.selectFrom(currencyConversionRate)
                .where(currencyConversionRate.from.code.eq(fromCode)
                        .and(currencyConversionRate.to.code.eq(toCode)))
                .fetchOne();
    }
}

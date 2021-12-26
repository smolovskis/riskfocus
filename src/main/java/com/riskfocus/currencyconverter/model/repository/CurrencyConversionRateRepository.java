package com.riskfocus.currencyconverter.model.repository;

import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import com.riskfocus.currencyconverter.model.repository.custom.CurrencyConversionRateRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyConversionRateRepository
        extends JpaRepository<CurrencyConversionRate, Long>, CurrencyConversionRateRepositoryCustom {

}

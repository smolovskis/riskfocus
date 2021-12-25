package com.riskfocus.currencyconverter.model.repository;

import com.riskfocus.currencyconverter.model.Currency;
import com.riskfocus.currencyconverter.model.CurrencyConversionRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyConversionRateRepository extends JpaRepository<CurrencyConversionRate, Long> {

    Optional<CurrencyConversionRate> findCurrencyConversionRateByFromCodeAndToCode(String from, String to);
}

package com.riskfocus.currencyconverter.model.repository;

import com.riskfocus.currencyconverter.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}

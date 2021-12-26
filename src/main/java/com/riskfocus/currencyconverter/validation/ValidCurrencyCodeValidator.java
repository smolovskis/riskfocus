package com.riskfocus.currencyconverter.validation;

import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ValidCurrencyCodeValidator implements ConstraintValidator<SupportedCurrency, String> {

    private final CurrencyRepository repository;

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(String currencyCode, ConstraintValidatorContext constraintValidatorContext) {
        return repository.findCurrencyByCode(currencyCode)
                .isPresent();
    }
}

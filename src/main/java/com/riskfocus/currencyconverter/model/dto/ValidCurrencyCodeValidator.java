package com.riskfocus.currencyconverter.model.dto;

import com.riskfocus.currencyconverter.model.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ValidCurrencyCodeValidator implements ConstraintValidator {

    private final CurrencyRepository
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}

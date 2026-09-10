package com.codingshuttle.springbootwebtutorial.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeSalaryValidator implements ConstraintValidator<EmployeeSalaryValadation, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value >= 0 && value <= 70000;
    }
}

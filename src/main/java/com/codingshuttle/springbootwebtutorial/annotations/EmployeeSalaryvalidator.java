package com.codingshuttle.springbootwebtutorial.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeSalaryvalidator implements ConstraintValidator<EmployeeRoleValidation, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= 0 && value <= 10000;
    }
}

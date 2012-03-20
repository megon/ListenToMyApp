package com.listenMyApp.core.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.listenMyApp.core.domain.EnvironmentType;

public class CheckEnvironmentValidator implements ConstraintValidator<CheckEnvironment, String>{

	@Override
	public void initialize(CheckEnvironment constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null){
			return true;
		}
		boolean validation = false;
		
		for (EnvironmentType environmentType : EnvironmentType.values()){
			if (environmentType.toString().equals(value.toUpperCase())){
				validation = true;
			}
		}
		return validation;
	}
}

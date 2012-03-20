package com.listenMyApp.core.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.listenMyApp.core.domain.Category;

public class CheckCategoryValidator implements ConstraintValidator<CheckCategory, String>{

	@Override
	public void initialize(CheckCategory constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null){
			return true;
		}
		boolean validation = false;
		
		for (Category category : Category.values()){
			if (category.toString().equals(value.toUpperCase())){
				validation = true;
			}
		}
		return validation;
	}
}

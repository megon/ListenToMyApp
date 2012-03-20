package com.listenMyApp.core.domain.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckCategoryValidator.class)
public @interface CheckCategory {
	
	String message() default"error.category.invalid";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default{};
}

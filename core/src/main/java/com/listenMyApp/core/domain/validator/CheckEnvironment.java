package com.listenMyApp.core.domain.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckEnvironmentValidator.class)
public @interface CheckEnvironment {
	
	String message() default"error.environment.invalid";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default{};
}

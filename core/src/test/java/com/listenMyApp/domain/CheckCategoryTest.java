package com.listenMyApp.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.listenMyApp.core.domain.Category;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventStatus;


public class CheckCategoryTest {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setup(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	
	@Test
	public void createEventWithInvalidCategory() throws Exception{
		final Event event = new Event("apiVersion"
				, "clientName"
				, "clientVersion"
				, "hml"
				, "invalidCategory"
				, "message"
				, new Date()
				, "className"
				, "fileName"
				, "lineNumber"
				, "methodName"
				, "trace"
				, EventStatus.OPEN);
		
		event.setProjectKey("test");
		Set<ConstraintViolation<Event>> violations = validator.validate(event);
		assertEquals(1, violations.size());
		assertEquals("error.category.invalid", violations.iterator().next().getMessage());
	}
	
	@Test
	public void createEventWithValidCategory() throws Exception{
		final Event event = new Event("apiVersion"
				, "clientName"
				, "clientVersion"
				, "hml"
				, Category.INFO.toString()
				, "message"
				, new Date()
				, "className"
				, "fileName"
				, "lineNumber"
				, "methodName"
				, "trace"
				, EventStatus.OPEN);
		
		event.setProjectKey("test");
		Set<ConstraintViolation<Event>> violations = validator.validate(event);
		assertEquals(0, violations.size());
	}
}

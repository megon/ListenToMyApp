package com.listenMyApp.domain;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.listenMyApp.core.domain.Category;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventStatus;


public class CheckEnvironmentTest {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setup(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void createEventWithInvalidEnvironment() throws Exception{
		final Event event = new Event("apiVersion"
				, "clientName"
				, "clientVersion"
				, "environment"
				, Category.DEBUG.toString()
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
		assertEquals("error.environment.invalid", violations.iterator().next().getMessage());
	}
	
	@Test
	public void createEventWithUpperEnvironment() throws Exception{
		final Event event = new Event("apiVersion"
				, "clientName"
				, "clientVersion"
				, "DES"
				, Category.DEBUG.toString()
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
	
	@Test
	public void createEventWithLowerEnvironment() throws Exception{
		final Event event = new Event("apiVersion"
				, "clientName"
				, "clientVersion"
				, "hml"
				, Category.DEBUG.toString()
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

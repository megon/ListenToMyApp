package com.listenMyApp.xml;

import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;

import com.listenMyApp.core.domain.Category;
import com.listenMyApp.core.domain.Event;
import com.thoughtworks.xstream.XStream;



public class EventTest {
	
	@Test
	public void marshalEventTest(){
		final Event event = new Event("projectKey"
					, "environment"
					, "apiVersion"
					, "clientName"
					, "clientVersion"
					, Category.INFO.toString()
					, "message"
					, new Date()
					, "className"
					, "fileName"
					, "lineNumber"
					, "methodName"
					, "trace"); 
		
		final XStream xstream = new XStream();
		xstream.processAnnotations(Event.class);
		final String eventXML = xstream.toXML(event);
		
		assertTrue(eventXML.contains("projectKey"));
		assertTrue(eventXML.contains("<event>"));
		assertTrue(eventXML.contains("<message>"));
		assertTrue(eventXML.contains("<eventDate>"));
		assertTrue(eventXML.contains("<className>"));
		assertTrue(eventXML.contains("<fileName>"));
		assertTrue(eventXML.contains("<lineNumber>"));
		assertTrue(eventXML.contains("<methodName>"));
		assertTrue(eventXML.contains("<trace>"));
	}
	
	@Test
	@SuppressWarnings("unused")
	public void testXML(){
		final XStream xstream = new XStream();
		xstream.processAnnotations(Event.class);
		final Event event = (Event)xstream.fromXML("<event></event>");
	}
}

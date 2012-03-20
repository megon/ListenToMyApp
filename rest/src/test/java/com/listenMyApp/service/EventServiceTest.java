package com.listenMyApp.service;

import java.util.Date;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import com.listenMyApp.core.domain.Category;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.rest.service.impl.EventServiceImpl;
import com.thoughtworks.xstream.XStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/contextTest.xml")
public class EventServiceTest {
	
	final TJWSEmbeddedJaxrsServer tjws = new TJWSEmbeddedJaxrsServer();
	
	@Before
	public void startup(){
		tjws.setPort(9093);
		tjws.getDeployment().getActualResourceClasses().add(EventServiceImpl.class);
		tjws.start();
	}
	
	@Test
	@Ignore
	public void createEvent() throws Exception{

		ClientRequest request = new ClientRequest("http://localhost:9093/events");
		request.body("application/xml", createXML());
		ClientResponse<String> response = request.post(String.class);
		
		assertTrue(response.getEntity().contains("<id>"));
		assertEquals("OK", response.getResponseStatus().toString());
		assertEquals(200, response.getStatus());
	}
	
	@After
	public void shutdown() throws Exception{
		tjws.stop();
	}
	
	private String createXML(){
		final Event event = new Event("projectKey"
				, "apiVersion"
				, "clientName"
				, "clientVersion"
				, "environment"
				, Category.INFO.toString()
				, "message"
				, new Date()
				, "className"
				, "fileName"
				, "lineNumber"
				, "methodName"
				, "trace");
		
		XStream xstream = new XStream();
		return xstream.toXML(event);
	}
}
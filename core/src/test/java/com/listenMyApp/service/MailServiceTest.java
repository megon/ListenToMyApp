package com.listenMyApp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.listenMyApp.core.domain.Category;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventException;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.core.domain.Project;
import com.listenMyApp.core.infra.mail.MailService;
import com.listenMyApp.core.infra.mail.impl.MailServiceException;

import freemarker.template.Configuration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/coreContextTest.xml")
public class MailServiceTest {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private Configuration freeMarkerConfiguration;
	
	
	@Test
	public void sendEventMail() throws MailServiceException, Exception{
		final String mailTo = "mrmarcondes@gmail.com";
		final String mailFrom = "event-noreply@listenmyapp.com";
		final String subject = "listenMyApp - New event notification";
		
		final Project project = new Project();
		project.setName("project");
		final Event event = new Event("apiVersion", "clientName", "clientVersion", "environment", Category.DEBUG.toString() 
				, "message", new Date(), "className", "fileName", 
				"lineNumber", "mehotdName", "trace", EventStatus.OPEN);
		event.setProject(project);
		mailService.send(mailTo, mailFrom, subject, getBody(event));
	}
	
	
	private String getBody(Event event) throws EventException{
		try{
			final String body = FreeMarkerTemplateUtils.processTemplateIntoString(
					freeMarkerConfiguration.getTemplate("eventMailTemplate.html")
					, getEventModel(event));
			return body;
		}
		catch (Exception ex){
			throw new EventException("error.MailService.generatingTemplateError");
		}
	}
	
	private Map<String, String> getEventModel(Event event){
		final Map<String, String> eventModel = new HashMap<String, String>();
		eventModel.put("environment", event.getEnvironment());
		eventModel.put("message", event.getMessage());
		eventModel.put("eventDate", event.getEventDate().toString());
		eventModel.put("className", event.getClassName());
		eventModel.put("fileName", event.getFileName());
		eventModel.put("methodName", event.getMethodName());
		eventModel.put("trace", event.getTrace());
		eventModel.put("project", event.getProject().getName());
		eventModel.put("status", event.getStatus().name());
		return eventModel;
	}		
	
	
}

package com.listenMyApp.rest.service.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.listenMyApp.core.application.EventFacade;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventException;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.rest.service.EventService;
import com.thoughtworks.xstream.XStream;

@Service
@Path("/events")
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventFacade eventFacade;
	
	private final XStream xstream = new XStream();
	
	@Override
	@POST
	@Consumes("application/xml")
	@Produces("application/xml")	
	public String create(String xml) {
		try{
			final Event event = toEvent(xml);
			final Event createdEvent = eventFacade.create(event.getProjectKey()
					, event.getEnvironment()
					, event.getApiVersion()
					, event.getClientName()
					, event.getClientVersion()
					, event.getCategory()
					, event.getMessage()
					, event.getClassName()
					, event.getFileName()
					, event.getLineNumber()
					, event.getMethodName()
					, event.getTrace());
			return toXML(createdEvent);
		}
		catch (ProjectException pe){
			throw new EventServiceException(Status.NOT_FOUND, pe.getMessage());
		}
		catch (EventException ee){
			throw new EventServiceException(Status.BAD_REQUEST, ee.getMessage());
		}
		catch (Exception ex){
			throw new EventServiceException(Status.BAD_REQUEST);
		}
	}
	
	private Event toEvent(String xml) throws Exception{
//		xstream.processAnnotations(Event.class);
		xstream.alias("event", Event.class);
		try{
			return (Event)xstream.fromXML(xml);
		}
		catch (Exception ex){
			ex.printStackTrace();
			throw new Exception(ex);
		}
	};
	
	private String toXML(Event event){
		xstream.processAnnotations(Event.class);
		xstream.omitField(Event.class, "project");
		return xstream.toXML(event);
	};
}

package com.listenMyApp.facade;

import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.listenMyApp.core.application.EventFacade;
import com.listenMyApp.core.domain.Category;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventException;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.UserException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/coreContextTest.xml")
@SuppressWarnings("unused")
public class EventFacadeTest {

	// notificar usuario ao criar um event
	
	@Autowired
	private EventFacade eventFacade;
	
	private static IDatabaseTester databaseTester;
		
	
	@Before
	public void initializeDB() throws Exception{
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
	            "jdbc:hsqldb:mem:defaultDB", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/eventDataSet.xml"));
		
        databaseTester.setDataSet( dataSet );
        databaseTester.onSetup();
	}		
	
	// criar um novo event, informando um projeto key (PROJECT_KEY)
	@Test
	public void createEvent() throws EventException, ProjectException{
		final String environment = "des";
		final String apiVersion = "apiVersion";
		final String clientName = "clientName";
		final String clientVersion = "clientVersion";
		final String message = "MESSAGE";
		final String className = "className";
		final String fileName = "fileName";
		final String lineNumber = "10";
		final String methodName = "methodName";
		final String trace = "trace";
		final String projectKey = "LMA";
		
		Event event = eventFacade.create(projectKey
										, environment
										, apiVersion
										, clientName
										, clientVersion
										, Category.DEBUG.toString()
										, message
										, className
										, fileName
										, lineNumber
										, methodName
										, trace);
		assertNotNull(event.getId());
		assertEquals(EventStatus.OPEN, event.getStatus());
	}
	
	
	// criar uma nova exception, informando um projeto key invalido.
	@Test (expected=ProjectException.class)
	public void createEventWithInvalidProjectKey() throws EventException, ProjectException{
		final String environment = "TEST";
		final String apiVersion = "apiVersion";
		final String clientName = "clientName";
		final String clientVersion = "clientVersion";
		final String message = "MESSAGE";
		final String className = "className";
		final String fileName = "fileName";
		final String lineNumber = "10";
		final String methodName = "methodName";
		final String trace = "trace";
		final String projectKey = "invalidKey";
		
		try{
			
			final Event event = eventFacade.create(projectKey
											, environment
											, apiVersion
											, clientName
											, clientVersion
											, Category.INFO.toString()
											, message
											, className
											, fileName
											, lineNumber
											, methodName
											, trace);
		}
		catch (Exception ex){
			assertTrue("error.project.notFound".equals(ex.getMessage()));
			throw new ProjectException(ex.getMessage());
		}		
	}
	
	//pesquisar um Event
	
	
	// alterar status de um event
	@Test
	public void close() throws UserException, EventException{
		String userKey = "ABC";
		Long eventId = 1L;
		
		final Event event = eventFacade.close(userKey, eventId);
		assertEquals(EventStatus.CLOSED, event.getStatus());
	}
	
	
	// alterar status de um event para um usuario invalido
	@Test(expected=UserException.class)
	public void closeInvalidUserKey() throws UserException, EventException{
		String userKey = "DBC";
		Long eventId = 1L;
		
		try{
			final Event event = eventFacade.close(userKey, eventId);
		}
		catch (Exception ex){
			assertTrue("error.user.notFound".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}
	}
	

	// alterar status de um event para um usuario valido e um event invalido
	@Test(expected=EventException.class)
	public void closeInvalidEventId() throws UserException, EventException{
		String userKey = "ABC";
		Long eventId = 200L;
		
		try{
			final Event event = eventFacade.close(userKey, eventId);
		}
		catch (Exception ex){
			assertTrue("error.event.notFound".equals(ex.getMessage()));
			throw new EventException(ex.getMessage());
		}
	}
	

	// alterar status de um event para um usuario valido e um event invalido
	@Test
	public void getLastOpenedEventsByUserKey() throws UserException, EventException{
		String userKey = "ABC";
		Long eventId = 2L;
		
		final List<Event> events = eventFacade.getLastOpened(userKey);
		assertTrue(events.size()==4);
	}
	

	@Test
	public void getEmptyLastOpenedEventsByUserKey() throws UserException, EventException{
		String userKey = "ABCDE";
		Long eventId = 2L;
		
		final List<Event> events = eventFacade.getLastOpened(userKey);
		assertTrue(events.size() == 0);
	}
	
	@Test
	public void getLast15OpenedEventsByUserKey() throws UserException, EventException{
		String userKey = "ABCDEF";
		Long eventId = 2L;
		
		final List<Event> events = eventFacade.getLastOpened(userKey);
		assertTrue(events.size()==15);
	}
	
	@Test
	public void getEventsByProjectKey() throws ProjectException{
		final String projectKey = "LMA";
		List<Event> events = eventFacade.getByProjectKey(projectKey);
		assertTrue (events.size() == 4);
	}
	

	@Test
	public void getByProjectKeyWithoutEvents() throws ProjectException{
		final String projectKey = "LM";
		List<Event> events = eventFacade.getByProjectKey(projectKey);
		assertTrue (events.size() == 0);
	}
	
	
	@Test(expected = ProjectException.class)
	public void getEventsByInvalidProjectKey() throws ProjectException{
		final String projectKey = "MMMA";
		final List<Event> events = eventFacade.getByProjectKey(projectKey);
	}
	
	@Test
	public void getCntOpenedEvents() throws EventException{
		final Long projectId = 1L;
		final Long cnt = eventFacade.getCntOpenedEvents(projectId);
		assertTrue(cnt == 3);
	}
	
	@Test
	public void filterByStatusOpened() throws EventException{
		final Long projectId = 1L;
		final List<Event> events = eventFacade.filterByStatus(projectId, EventStatus.OPEN);
		assertTrue(events.size() == 3);
	}
	
	@Test
	public void filterByAllStatus() throws EventException{
		final Long projectId = 1L;
		final List<Event> events = eventFacade.filterByStatus(projectId);
		assertTrue(events.size() == 4);
	}

	
	@Test
	public void filterByStatusClosed() throws EventException{
		final Long projectId = 1L;
		final List<Event> events = eventFacade.filterByStatus(projectId, EventStatus.CLOSED);
		assertTrue(events.size() == 1);
	}
	
	@Test
	public void getEventsByProjectId() throws ProjectException{
		final Long projectId = 1L;
		final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		List<Event> events = eventFacade.getByProjectId(projectId);
		assertTrue (events.size() == 4);
	}
	
	@Test
	public void getEventById() throws EventException{
		final Long eventId = 1L;
		final Event event = eventFacade.get(eventId);
		assertTrue (event.getId().equals(eventId));
	}
	
}

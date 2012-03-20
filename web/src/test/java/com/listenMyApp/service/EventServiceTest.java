package com.listenMyApp.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;


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

import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.dto.EventStatusDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/contextTest.xml")
public class EventServiceTest {
	
	@Autowired
	private EventService eventService;
	
	private static IDatabaseTester databaseTester;
		
	
	@Before
	public void initializeDB() throws Exception{
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
	            "jdbc:hsqldb:mem:defaultDB", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/eventDataSet.xml"));
		
//		IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(""));
        databaseTester.setDataSet( dataSet );
        databaseTester.onSetup();
	}	
	
	@Test
	public void getEventsByProjetKey() throws ServiceException{
		final String projectKey = "LMA";
		final List<EventDTO> events = eventService.getByProjectKey(projectKey);
		assertTrue(events.size() == 3);
	}
	
	@Test
	public void getLastOpened() throws ServiceException{
		final String userKey = "ABC";
		final List<EventDTO> events = eventService.getLastOpened(userKey);
		assertTrue(events.size() == 1);
	}
	
	@Test
	public void closeEvent() throws ServiceException{
		final String userKey = "ABC";
		final Long eventId = 1L;
		final EventDTO closedEvent = eventService.close(userKey, eventId);
		assertTrue(closedEvent.getStatus().getValue() == 2);
	}
	
	@Test
	public void getCntOpenedEvents() throws ServiceException{
		final Long projectId = 1L;
		final Long cnt = eventService.getCntOpenedEvents(projectId);
		assertTrue(cnt == 1);
	}
	
	@Test
	public void filterByStatusOpened() throws ServiceException{
		final Long projectId = 1L;
		final List<EventDTO> events = eventService.filterByStatus(projectId, EventStatusDTO.OPEN);
		assertTrue(events.size() == 1);
	}
	
	@Test
	public void filterByStatusClosed() throws ServiceException{
		final Long projectId = 1L;
		final List<EventDTO> events = eventService.filterByStatus(projectId, EventStatusDTO.CLOSED);
		assertTrue(events.size() == 2);
	}
	
	@Test
	public void filterAllStatus() throws ServiceException{
		final Long projectId = 1L;
		final List<EventDTO> events = eventService.filterByStatus(projectId);
		assertTrue(events.size() == 3);
	}
	
	
	@Test
	public void getByProjectId() throws ServiceException{
		final Long projectId = 1L;
		final List<EventDTO> events = eventService.getByProjectId(projectId);
		assertTrue(events.size() == 3);
	}
	
}

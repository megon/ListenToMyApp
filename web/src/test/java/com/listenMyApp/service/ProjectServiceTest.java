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

import com.listenMyApp.dto.ProjectDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/contextTest.xml")
public class ProjectServiceTest {
	
	@Autowired
	private ProjectService projectService;
	
	private static IDatabaseTester databaseTester;
		
	
	@Before
	public void initializeDB() throws Exception{
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
	            "jdbc:hsqldb:mem:defaultDB", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/projectDataSet.xml"));
		
//		IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(""));
        databaseTester.setDataSet( dataSet );
        databaseTester.onSetup();
	}	
	
	@Test
	public void createProject() throws ServiceException{
		final String userKey = "ABC";
		final ProjectDTO projectDTO = new ProjectDTO("projectName");
		final ProjectDTO createdProjectDTO = projectService.create(userKey, projectDTO);
		
		assertNotNull(createdProjectDTO.getKey());
		
	}
	
	@Test
	public void deleteProject() throws ServiceException{
		final String userKey = "ABC";
		projectService.remove(userKey, 1L);
	}
	
	@Test
	public void getProjectsByUserKey() throws ServiceException{
		final String userKey = "ABC";
		final List<ProjectDTO> projects = projectService.getByUserKey(userKey);
		assertTrue(projects.size() == 2);
		assertTrue(projects.get(0).getCntOpenedEvents() == 1);
	}
	
	@Test
	public void addUserToProject() throws ServiceException{
		final String userKey = "DEF";
		final String email = "email2@email.com";
		final Long idProject = 1L;
		
		projectService.addUserToProject(userKey, idProject, email);
	}
	
	@Test
	public void generateKey() throws ServiceException{
		final String userKey = "ABC";
		final Long projectId = 1L;
		final ProjectDTO project = projectService.get(userKey, projectId);
		final String key = projectService.generateKey(userKey, projectId);
		assertTrue(!project.getKey().equals(key));
		
		
	}
	
}

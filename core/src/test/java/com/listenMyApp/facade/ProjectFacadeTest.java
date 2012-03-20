package com.listenMyApp.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import com.listenMyApp.core.application.ProjectFacade;
import com.listenMyApp.core.domain.Project;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.UserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/coreContextTest.xml")
public class ProjectFacadeTest {
	
	@Autowired
	private ProjectFacade projectFacade;
	
	private static IDatabaseTester databaseTester;
		
	
	@Before
	public void initializeDB() throws Exception{
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:defaultDB", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/projectDataSet.xml"));
		databaseTester.setDataSet( dataSet );
        databaseTester.onSetup();
	}	
	
	@Test
	public void createProject() throws ProjectException, UserException{
		final String userKey = "ABC";
		final String projectName = "My project sample";
		final Project project = projectFacade.create(userKey, projectName);
		assertEquals(project.getName(), projectName);
		assertNotNull(project.getKey());
		assertNotNull(project.getId());
	}
	
	@Test(expected=ProjectException.class)
	public void createProjectHitMaximunNumberOfProjects() throws ProjectException, UserException {
		String userKey = "XYZ";
		String projectNameOne = "My project";
		try {
			projectFacade.create(userKey, projectNameOne);		
		} catch (Exception ex){
			assertTrue("error.project.hit.limit".equals(ex.getMessage()));
			throw new ProjectException(ex.getMessage());
		}
	}
	
	@Test
	public void deleteProject() throws ProjectException, UserException {
		Long projectId = 2L;
		String userKey = "ABC";
		projectFacade.delete(userKey, projectId);
	}
	
	@Test
	public void findProjectEvents() throws ProjectException, UserException {
		Long projectId = 3L;
		String userKey = "CDE";
		final Project project = projectFacade.get(userKey, projectId);
		assertEquals(3, project.getEvents().size());
	}
	
	@Test(expected=ProjectException.class)
	public void findProjectEventsWithWrongUserKeyAndValidProjectKey() throws ProjectException, UserException {
		final String userKey = "ABC";
		final Long projectId = 3L;
		final Project project = projectFacade.get(userKey, projectId);
		assertEquals(0, project.getEvents().size());
	}
	
	
	@Test(expected=ProjectException.class)
	public void findProjectEventsByInvalidId() throws ProjectException, UserException {
		Long projectId = 1000L;
		String userKey = "CDE";
		try{
			projectFacade.get(userKey, projectId);			
		} catch (Exception ex){
			assertTrue("error.project.notFound".equals(ex.getMessage()));
			throw new ProjectException(ex.getMessage());
		}		
	}

	//dado um email, pesquisar na base de usuario e adiciona-lo a um projeto se o numero de projetos nao foi atingido, erro caso contrario
	@Test
	public void addUserToProject() throws ProjectException, UserException {
		Long projectId = 3L;
		String userKey = "CDE";
		String emailUserToAdd = "marcelo@email.com.br";
		projectFacade.addUserToProject(userKey, projectId, emailUserToAdd);
	}

	@Test(expected=UserException.class)
	public void addUserToProjectByInvalidEmail() throws ProjectException, UserException {
		Long projectId = 3L;
		String userKey = "CDE";
		String emailUserToAdd = "blaks@email.com";
		try{
			projectFacade.addUserToProject(userKey, projectId, emailUserToAdd);
		} catch (Exception ex){
			assertTrue("error.user.email.notFound".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}	
	}
	
	@Test(expected=ProjectException.class)
	public void addUserToProjectHitLimit() throws ProjectException, UserException {
		Long projectId = 3L;
		String userKey = "CDE";
		String emailUserToAdd = "mike@email.com.br";
		
		try {
			projectFacade.addUserToProject(userKey, projectId, emailUserToAdd);
		} catch (Exception ex){
			assertTrue("error.project.hit.limit".equals(ex.getMessage()));
			throw new ProjectException(ex.getMessage());
		}
	}
	
	@Test
	public void getProjectsByUserKey() throws ProjectException, UserException{
		final String userKey = "CDE";
		
		final List<Project> projects = projectFacade.getByUserKey(userKey);
		assertTrue(projects.size() == 2);
		assertTrue(projects.get(0).getCntOpenedEvents() == 2);
	}
	
	@Test
	public void getByUserKeyWithoutProjects() throws ProjectException, UserException{
		final String userKey = "KLM";
		
		final List<Project> projects = projectFacade.getByUserKey(userKey);
		assertTrue(projects.size() == 0);
	}
	
	@Test (expected = UserException.class)
	@SuppressWarnings("unused")
	public void getProjectsByInvalidUserKey() throws ProjectException, UserException{
		final String userKey = "CDEFFF";
		final List<Project> projects = projectFacade.getByUserKey(userKey);
	}
	
	@Test(expected = ProjectException.class)
	public void generateKeyWithWrongUserKeyAndValidProjectKey() throws Exception{
		final String userKey = "KLM";
		final Long projectId = 1L;
		final Project project = projectFacade.get(userKey, projectId);
		final String projectKey = project.getKey();
		
		final String key = projectFacade.generateKey(userKey, projectId);
		assertTrue (!projectKey.equals(key));
	}
	
	@Test
	public void generateKey() throws Exception{
		final String userKey = "ABC";
		final Long projectId = 2L;
		final Project project = projectFacade.get(userKey, projectId);
		final String projectKey = project.getKey();
		
		final String key = projectFacade.generateKey(userKey, projectId);
		assertTrue (!projectKey.equals(key));
	}

	
	@Test
	public void alterProjectInformation() throws Exception{
		final String userKey = "CDE";
		final Long projectId = 3L;
		final String projectName = "SharePoints";
		final String projectKey = "axkblm";
		
		projectFacade.alter(userKey, projectId, projectName, projectKey);
		
		Project project = projectFacade.get(userKey, projectId);
		
		assertEquals(project.getName(), projectName);
		assertEquals(project.getKey(), projectKey);

	}

	@Test
	public void getProjectByKey() throws Exception{
		Long projectId = 3L;
		String userKey = "CDE";
		final Project project = projectFacade.get(userKey, projectId);
		assertEquals(1L, project.getCntClosedEvents().longValue());
		assertEquals(2L, project.getCntOpenedEvents().longValue());
		assertEquals(3L, project.getCntTotalEvents().longValue());
	}
	
	
	// 	pesquisar Projeto, informando uma project key invalida.
	//
}

package com.listenMyApp.service;

import static org.junit.Assert.*;

import java.io.File;
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

import com.listenMyApp.dto.LanguageDTO;
import com.listenMyApp.dto.UserDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/contextTest.xml")
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	private static IDatabaseTester databaseTester;
		
	
	@Before
	public void initializeDB() throws Exception{
		databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
	            "jdbc:hsqldb:mem:defaultDB", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/userDataSet.xml"));
		
//		IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(""));
        databaseTester.setDataSet( dataSet );
        databaseTester.onSetup();
	}	
	
	@Test
	public void createUser() throws ServiceException{
		final String idTimeZone = TimeZone.getDefault().getID();

		final UserDTO userDTO = new UserDTO("Marco Rojo", "marco@megon.com.br", "password", 1L, "en", idTimeZone, true);
		final UserDTO createdUserDTO = userService.create(userDTO);
		
		assertNotNull(createdUserDTO.getKey());
		assertTrue(createdUserDTO.getIdTimeZone().equals("America/Sao_Paulo"));
	}
	

	@Test
	public void login() throws ServiceException{
		final UserDTO userDTO = new UserDTO("email@email.com", "password");
		final UserDTO loggedUserDTO = userService.login(userDTO);
		
		assertNotNull(loggedUserDTO.getKey());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void changePassword() throws ServiceException{
		final String email = "email@email.com";
		final String oldPassword = "password";
		final String newPassword = "newPassword";
		
		userService.changePassword(email, oldPassword, newPassword);
		
		final UserDTO userDTO = new UserDTO(email, newPassword);
		final UserDTO loggedInUserDTO = userService.login(userDTO);
	}
	
	@Test
	public void forgetMyPasswordTest() throws ServiceException{
		final String email = "mrmarcondes@gmail.com";
		userService.forgetPassword(email);
	}
	
	
	@Test
	public void activateUserTest() throws ServiceException{
		final String userKey = "DEF";
		
		final UserDTO user = userService.activate(userKey);
		assertTrue("not validated", user.isActive());
	}
	
	@Test
	public void alterUser() throws ServiceException{
		final String key = "ABC";
		final String name = "Marco Rojo Alterado";
		final String password = "password";
		final String language = "en";
		final String idTimeZone = "Pacific/Kiritimati";
		final String email = "email@email.com";
		final UserDTO userDTO = new UserDTO(email, password);
		
		userService.alter(key, name, language, idTimeZone, false);
		final UserDTO alteredUser = userService.login(userDTO); 
		
		assertTrue(alteredUser.getName().equals("Marco Rojo Alterado"));
		assertTrue(alteredUser.getLanguage().equals(LanguageDTO.en));
		assertTrue(alteredUser.getIdTimeZone().equals("Pacific/Kiritimati"));
		assertTrue(alteredUser.getNotify() == false);
		
	}
	
	
}

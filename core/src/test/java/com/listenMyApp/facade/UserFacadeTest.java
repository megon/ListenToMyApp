package com.listenMyApp.facade;

import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.transaction.TransactionSystemException;

import com.listenMyApp.core.application.UserFacade;
import com.listenMyApp.core.domain.Language;
import com.listenMyApp.core.domain.User;
import com.listenMyApp.core.domain.UserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/coreContextTest.xml")
@SuppressWarnings("unused")
public class UserFacadeTest {

	@Autowired
	private UserFacade userFacade;
	
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
	public void validActiveLogin() throws UserException{
		final String email = "email@email.com";
		final String password = "password";
		final User user = userFacade.login(email, password);
		final Date date = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("hh");
		final Calendar cal = Calendar.getInstance();
		
		assertEquals(user.getKey(), "ABCD");
		assertTrue(user.getProjects().size() != 0);
		assertTrue(user.getLanguage().equals(Language.pt));

	}
	
	@Test(expected=UserException.class)
	public void validInatciveLogin() throws UserException{
		String email = "inactive@email.com";
		String password = "password";
		try{
			User user = userFacade.login(email, password);
		}
		catch (Exception ex){
			assertTrue("error.user.notActive".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}
	}
	
	@Test
	public void activateUserValidKey() throws UserException{
		final String userKey = "DEF";
		final User user = userFacade.activate(userKey);
		assertTrue(user.isActive());
	}
	
	@Test(expected=UserException.class)
	public void activateUserInvalidKey() throws UserException{
		final String userKey = "";
		try{
			final User user = userFacade.activate(userKey);
		}
		catch (Exception ex){
			assertTrue("error.user.notFound".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}
		
	}
	
	
	@Test(expected=UserException.class)
	public void invalidLogin() throws UserException{
		String email = "email@notFound.com";
		String password = "invalidPassword";
		try{
			User user = userFacade.login(email, password);
		}
		catch (Exception ex){
			assertTrue("error.user.notFound".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}
	}
	
	@Test(expected=UserException.class)
	public void invalidPassword() throws UserException{
		String email = "email@email.com";
		String password = "invalidPassword";
		try{
			User user = userFacade.login(email, password);
		}
		catch (Exception ex){
			assertTrue("error.user.invalidPassword".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}	
	}
	
	@Test(expected=UserException.class)
	public void blankLogin() throws UserException{
		String email = "";
		String password = "password";
		try{
			User user = userFacade.login(email, password);
		}
		catch (Exception ex){
			assertTrue("error.user.notFound".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}	
	}
	
	@Test(expected=UserException.class)
	public void blankPassword() throws UserException{
		String email = "email@email.com";
		String password = "";
		try{
			User user = userFacade.login(email, password);
		}
		catch (Exception ex){
			assertTrue("error.user.invalidPassword".equals(ex.getMessage()));
			throw new UserException(ex.getMessage());
		}
	}
	
	@Test
	public void createNewUser() throws UserException{
		final String name = "Marco Rojo";
		final String password = "password";
		final String email = "mrmarcondes@gmail.com";
		final Long idAccount = 1L;
		final String language = "en";
		final String idTimeZone = TimeZone.getDefault().getID();

		
		final User user = userFacade.create(name, email, password, idAccount, language, idTimeZone, true);
		assertNotNull(user.getId());
		assertTrue(!user.isActive());
		assertTrue(user.getIdTimeZone().equals("America/Sao_Paulo"));
		assertTrue(user.isNotify() == true);
		assertNotNull(user.getRegister());
	}
	
	@Test(expected=UserException.class)
	public void createUserWithInvalidLanguage() throws UserException{
		final String name = "User Name";
		final String password = "newPassword";
		final String email = "newUserWithInvalidLanguage@gmail.com";
		final Long idAccount = 1L;
		final String language = "de";
		final String idTimeZone = TimeZone.getDefault().getID();
		
		try{
			final User user = userFacade.create(name, email, password, idAccount, language, idTimeZone, true);
		}
		catch (TransactionSystemException ex){
			assertTrue(ex.getApplicationException().getMessage().equals("error.user.invalidLanguage"));
			throw new UserException(ex.getMessage());
		}
	}
	

	
	@Test(expected=UserException.class)
	public void createRepeatedUser() throws UserException{
		final String name = "User Name";
		final String password = "newPassword";
		final String email = "email@email.com";
		final Long idAccount = 1L;
		final String language = "en";
		final String idTimeZone = TimeZone.getDefault().getID();

		
		try{
			final User user = userFacade.create(name, email, password, idAccount, language, idTimeZone, true);
		}
		catch (TransactionSystemException ex){
			assertTrue(ex.getApplicationException().getMessage().equals("error.user.persistError"));
			throw new UserException(ex.getMessage());
		}
		
	}
	
	
	@Test(expected=UserException.class)
	public void createUserWithInvalidAccount() throws UserException{
		final String name = "User Name";
		final String password = "newPassword";
		final String email = "userInvalidAccount@email.com";
		final Long idAccount = 10L;
		final String language = "pt";
		final String idTimeZone = TimeZone.getDefault().getID();
		
		try{
			final User user = userFacade.create(name, email, password, idAccount, language, idTimeZone, true);
		}
		catch (TransactionSystemException ex){
			assertTrue(ex.getApplicationException().getMessage().equals("error.user.persistError"));
			throw new UserException(ex.getMessage());
		}
		
	}
	
	

	@Test(expected=UserException.class)
	public void createUserWithInvalidEmail() throws UserException{
		final String name = "User Name";
		final String password = "newPassword";
		final String email = "email";
		final Long idAccount = 1L;
		final String language = "en";
		final String idTimeZone = TimeZone.getDefault().getID();
		
		try{
			final User user = userFacade.create(name, email, password,  idAccount, language, idTimeZone, true);
		}
		catch (TransactionSystemException ex){
			assertFalse(ex.getApplicationException().getMessage().equals("error.user.invalidEmail"));
			throw new UserException(ex.getMessage());
		}
	}
	
	@Test(expected=UserException.class)
	public void createUserWithInvalidPasswordSize() throws UserException{
		final String name = "User Name";
		final String password = "new";
		final String email = "email";
		final Long idAccount = 1L;
		final String language = "en";
		final String idTimeZone = TimeZone.getDefault().getID();

		
		try{
			final User user = userFacade.create(name, email, password,  idAccount, language, idTimeZone, true);
		}
		catch (TransactionSystemException ex){
			assertFalse(ex.getApplicationException().getMessage().equals("error.user.minPasswordSize"));
			throw new UserException(ex.getMessage());
		}
	}
	
	@Test
	public void changePasswordTest() throws UserException{
		final String email = "email@email.com";
		final String oldPassword = "password";
		final String newPassword = "newPassword";
		
		userFacade.changePassword(email, oldPassword, newPassword);
		userFacade.login(email, newPassword);
	}
	

	@Test (expected = UserException.class)
	public void changeInvalidOldPasswordTest() throws UserException{
		final String email = "email@email.com";
		final String oldPassword = "pass";
		final String newPassword = "newPass";
		
		try{
			userFacade.changePassword(email, oldPassword, newPassword);
		}
		catch (Exception ex){
			assertTrue(ex.getMessage().equals("error.user.invalidPassword"));
			throw new UserException(ex.getMessage());
		}
	}

	@Test (expected = UserException.class)
	public void changePasswordWithInvalidUserTest() throws UserException{
		final String email = "e@email.com";
		final String oldPassword = "pass";
		final String newPassword = "newPass";
	
		try{
			userFacade.changePassword(email, oldPassword, newPassword);
		}
		catch (Exception ex){
			assertTrue(ex.getMessage().equals("error.user.notFound"));
			throw new UserException(ex.getMessage());
		}
	}
	
	@Test
	public void forgetPassword() throws UserException{
		final String email = "marco@megon.com.br";
		userFacade.forgetPassword(email);
	}
	
	
	@Test
	public void updateUser() throws UserException{
		final String key = "ABC";
		final String name = "Marco Rojo Alterado";
		final String language = "en";
		final String idTimeZone = "Pacific/Kiritimati";
		final String email = "marco@megon.com.br";

		
		userFacade.alter(key, name, language, idTimeZone, false);
		final User user = userFacade.login(email, "password"); 
		
		assertTrue(user.getName().equals("Marco Rojo Alterado"));
		assertTrue(user.getLanguage().equals(Language.en));
		assertTrue(user.getIdTimeZone().equals("Pacific/Kiritimati"));
		assertTrue(user.isNotify() == false);
	}
	
}

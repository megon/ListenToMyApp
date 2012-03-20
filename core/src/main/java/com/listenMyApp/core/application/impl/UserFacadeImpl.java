package com.listenMyApp.core.application.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.listenMyApp.core.application.UserFacade;
import com.listenMyApp.core.domain.Account;
import com.listenMyApp.core.domain.Language;
import com.listenMyApp.core.domain.User;
import com.listenMyApp.core.domain.UserException;
import com.listenMyApp.core.domain.UserRepository;
import com.listenMyApp.core.infra.mail.MailService;
import com.listenMyApp.core.util.ManageKeyUtil;

@Service
public class UserFacadeImpl implements UserFacade {
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String PASSWORD_KEY = "PSSWD_KEY";
	
	@Autowired
	private MailService mailService;
	
	private static final String USER_CONFIRMATION_TEMPLATE_FILE_EN = "confirmationEmailTemplate_en.html"; 
	private static final String USER_CONFIRMATION_TEMPLATE_FILE_PT = "confirmationEmailTemplate_pt.html";
	private static final String USER_CONFIRMATION_SUBJECT_EN = "listenToMyApp - Confirmation email";
	private static final String USER_CONFIRMATION_SUBJECT_PT = "listenToMyApp - Confirmacao de cadastro";
	
	private static final String FORGET_PASSWORD_TEMPLATE_FILE_EN = "forgetPasswordTemplate_en.html"; 
	private static final String FORGET_PASSWORD_TEMPLATE_FILE_PT = "forgetPasswordTemplate_pt.html";
	private static final String FORGET_PASSWORD_SUBJECT_EN = "listenToMyApp - Forget password";
	private static final String FORGET_PASSWORD_SUBJECT_PT = "listenToMyApp - Esqueci minha senha";

	private static final String EMAIL_FROM = "listentomyapp@listentomyapp.com";

	
	private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = validatorFactory.getValidator();

	@Override
	@Transactional
	public User login(final String email, final String password) throws UserException {
		final User user = userRepository.login(email, cryptoPassword(password));
		return user;
	}

	@Override
	@Transactional(rollbackFor=UserException.class)
	public User create(String name, String email, String password, 
			Long idAccount, String language, String idTimeZone, Boolean notify) throws UserException {
		final User user = new User();
		final Account account = new Account();
		
		user.setName(name);
		user.setPassword(cryptoPassword(password));
		user.setEmail(email);
		user.createKey();
		user.setIdTimeZone(idTimeZone);
		user.setNotify(notify);
		
		account.setId(idAccount);
		user.setAccount(account);
		user.setActive(false);
		user.setLanguage(language);
		user.setRegister(new Date());

		validateUser(user);
		userRepository.save(user);
		sendConfirmationMail(user);
	
		return user;
	}
	
	@Override
	@Transactional
	public User activate(String userKey) throws UserException {
		final User user = userRepository.getByKey(userKey);
		user.setActive(true);
		return user;
	}
	
	
	@Override
	@Transactional
	public void changePassword(String email, String oldPassword,
			String newPassword) throws UserException {
		
		final User user = userRepository.login(email, cryptoPassword(oldPassword));
		user.setPassword(cryptoPassword(newPassword));
	}
	
	
	@Override
	@Transactional
	public void forgetPassword(String email) throws UserException {
		final User user = userRepository.getByEmail(email);
		final Random random = new Random();
		final Integer randomNumber = random.nextInt(999999);
		
		user.setPassword(cryptoPassword(randomNumber.toString()));
		sendForgetPasswordEmail(user, randomNumber);
	}	
	
	
	@Override
	@Transactional
	public void alter(String userKey, String name, 
			String language, String idTimeZone, Boolean notify) throws UserException {
		
		final User user = userRepository.getByKey(userKey);
		
		user.setName(name);
		user.setLanguage(language);
		user.setIdTimeZone(idTimeZone);
		user.setNotify(notify);
	}
	
	
	
	private void validateUser(final User user) throws UserException{
		final Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		final StringBuffer messages = new StringBuffer();

		if (constraintViolations.size() > 0){
			for (Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator(); iterator.hasNext();){
				messages.append(iterator.next().getMessage());
				messages.append(System.getProperty("line.separator"));
			}
			throw new UserException(messages.toString());
		}
	}
	
	private final String cryptoPassword(final String password){
		return ManageKeyUtil.createKey(password, PASSWORD_KEY);
	}
	
	
	private Map<String, String> getConfirmationEmailModel(User user){
		final Map<String, String> userModel = new HashMap<String, String>();
		userModel.put("name", user.getName());
		userModel.put("email", user.getEmail());
		userModel.put("userKey", user.getKey());
		return userModel;
	}	
	

	private Map<String, String> getForgetPasswordModel(User user, Integer password){
		final Map<String, String> userModel = new HashMap<String, String>();
		userModel.put("name", user.getName());
		userModel.put("email", user.getEmail());
		userModel.put("userKey", user.getKey());
		userModel.put("password", password.toString());
		return userModel;
	}	
	
	private void sendConfirmationMail(User user) throws UserException{
		try{
			if (user.getLanguage().equals(Language.en)){
				final String body = mailService.createBody(USER_CONFIRMATION_TEMPLATE_FILE_EN, getConfirmationEmailModel(user));
				mailService.send(user.getEmail(), EMAIL_FROM, USER_CONFIRMATION_SUBJECT_EN, body);
			}
			else {
				final String body = mailService.createBody(USER_CONFIRMATION_TEMPLATE_FILE_PT, getConfirmationEmailModel(user));
				mailService.send(user.getEmail(), EMAIL_FROM, USER_CONFIRMATION_SUBJECT_PT, body);
			}
			
		}
		catch (Exception ex){
			throw new UserException(ex.getMessage());
		}
	}

	private void sendForgetPasswordEmail(User user, Integer password) throws UserException{
		try{
			if (user.getLanguage().equals(Language.en)){
				final String body = mailService.createBody(FORGET_PASSWORD_TEMPLATE_FILE_EN, getForgetPasswordModel(user, password));
				mailService.send(user.getEmail(), EMAIL_FROM, FORGET_PASSWORD_SUBJECT_EN, body);
			}
			else {
				final String body = mailService.createBody(FORGET_PASSWORD_TEMPLATE_FILE_PT, getForgetPasswordModel(user, password));
				mailService.send(user.getEmail(), EMAIL_FROM, FORGET_PASSWORD_SUBJECT_PT, body);
			}
			
		}
		catch (Exception ex){
			throw new UserException(ex.getMessage());
		}
	}


}

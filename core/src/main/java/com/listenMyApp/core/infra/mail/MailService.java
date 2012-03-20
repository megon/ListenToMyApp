package com.listenMyApp.core.infra.mail;

import java.util.Map;

import com.listenMyApp.core.infra.mail.impl.MailServiceException;

/**
 * Classe de servico que permite enviar emails.
 * 
 * @author Marco Rojo
 * Data: 22/02/2010
 */
public interface MailService {

	
	/**Envia email
	 * 
	 * @param mailTo quem recebera o email.
	 * @param mailFrom quem esta enviando o email.
	 * @param subject assunto do email.
	 * @param body corpo do email.
	 * @exception MailServiceException quando ocorre alguma exception no envio do email.
	 */
	void send(String mailTo, String mailFrom, String subject, String body) throws MailServiceException;
	
	
	String createBody(String templateFileName, Map<String, String> variables) throws MailServiceException;

}

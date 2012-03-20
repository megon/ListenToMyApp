package com.listenMyApp.core.infra.mail.impl;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.listenMyApp.core.infra.mail.MailService;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Configuration freeMarkerConfiguration;
	
	
	@Override
	public void send(final String mailTo, final String mailFrom, final String subject, final String body) throws MailServiceException {
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				
				message.setSubject(subject);
				message.setTo(mailTo);
				message.setFrom(mailFrom);
				message.setText(body, true);
			}
		};
		mailSender.send(preparator);
	}


	@SuppressWarnings("unchecked")
	@Override
	public String createBody(String templateFileName, Map variables)
			throws MailServiceException {
		try {
			final String body = FreeMarkerTemplateUtils.processTemplateIntoString(
					freeMarkerConfiguration.getTemplate(templateFileName)
					, variables);
			return body;
		}
		catch (Exception ex){
			ex.printStackTrace();
			throw new MailServiceException("error.MailService.generatingTemplateError");
		}
		
		
	}

}

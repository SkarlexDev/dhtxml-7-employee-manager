package com.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bean.Employee;

public class MailServiceImpl implements Runnable {

	private final Employee bean;
	
	public MailServiceImpl(Employee bean) {
		this.bean = bean;
	}
	@Override
	public void run() {
		
		String targetEmail = bean.getEmail();	
		
		final String username = "fast6trade@gmail.com";
		final String password = "qcgurscfjnnrrest";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmail));
			message.setSubject("Account activation");
			message.setContent(""					
					+"<center>\r\n"
					+ "<h3>Firm account created</h3>\r\n"
					+ "<p>Username: "+ bean.getEmail() + "</p>\r\n"
					+ "<p>Password: "+ bean.getPassword() + "</p>\r\n"
					+ "<a class=\"button\" href=\"http://localhost:8080/employee-manager/activate?key="+ bean.getActivation_key()+ "\"><button>Activate</button></a>\r\n"
					+ "</center>"
					+ "","text/html");
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}	
}

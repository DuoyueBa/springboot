package com.cct.mails;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	private static Session session;
	
	private static EmailUtil instance = new EmailUtil();
	
	// just temporary smtp server
	// TODO: move properties to prop file.
	private String smtpHostServer = "smtp.example.com";
	
	public static EmailUtil getInstance() {
		return instance;
	}
	
	private EmailUtil() {
		Properties props = System.getProperties();
	    props.put("mail.smtp.host", smtpHostServer);
	    session = Session.getInstance(props, null);
	}
	/**
	 * Utility method to send simple HTML email
	 * @param mail
	 */
	public boolean sendEmail(Mail mail){
		try {
			MimeMessage msg = new MimeMessage(session);
			//set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(mail.getFromMail(), "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse(mail.getFromMail(), false));

			msg.setSubject(mail.getSubject(), "UTF-8");

			msg.setText(mail.getBody(), "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getToMail(), false));
			System.out.println("Message is ready");
			Transport.send(msg);  

			System.out.println("EMail Sent Successfully!!");
			return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return false;
	    }
	}
	
	
}

package com.cct.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cct.mails.Mail;
import com.cct.mails.EmailUtil;

@RestController
public class MailUtilsController {
	
	@RequestMapping(method = RequestMethod.POST, value="/sendmail")
	public boolean sendMail(@RequestBody Mail mail) {
		System.out.println("Send mail");
		return EmailUtil.getInstance().sendEmail(mail);
	}

}

package com.cct.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cct.beans.exceptions.DataViolationException;
import com.cct.beans.exceptions.UserAlreadyExistException;
import com.cct.beans.exceptions.UserNotFoundException;
import com.cct.mails.Mail;

public class UserRegistration {
	private List<User> userRecords;
	
	private static UserRegistration usrregd = new UserRegistration();
	
	private String SEND_MAIL_URL;
	
	private final String UPDATE_SUCCESS = "Update successful";
	
	private final String UPDATE_FAILURE = "Update un-successful";
	
	private final String DELETE_SUCCESS = "Delete successful";
	
	private final String DELETE_FAILURE = "Delete un-successful";
	
	private UserRegistration() {
		userRecords = new ArrayList<User>();
		if (System.getenv("MAIL_HOST") != null) {
			SEND_MAIL_URL = "http://" + System.getenv("MAIL_HOST") + ":8084/sendmail";
		} else {
			SEND_MAIL_URL = "http://localhost:8084/sendmail";
		}
		System.out.println("Mail host: " + SEND_MAIL_URL);
	}
	
	public static UserRegistration getInstance() {
		return usrregd;
	}
	/**
	 * TODO: Now we just store users in a list and check existing user by email
	 *       In next step, we can store users in DB and use DB constraint to check
	 *       whether user already exists.
	 * @param usr
	 */
	public void add(User usr) {
		if (usr.getName() == null) {
			throw new DataViolationException("User name must not be empty.");
		} else if (usr.getEmail() == null) {
			throw new DataViolationException("User email must not be empty.");
		} else if (!usr.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new DataViolationException("User email format is wrong.");
		}
		for (User each : userRecords) {
			if (each.getEmail().equals(usr.getEmail())) {
				throw new UserAlreadyExistException("User alread exists.");
			}
		}
		userRecords.add(usr);
		RestTemplate restTemplate = new RestTemplate();
		Mail mail = new Mail();
		mail.setFromMail("no_reply@example.com");
		mail.setToMail(usr.getEmail());
		mail.setSubject("Register new user");
		mail.setBody("Register new user");
		try {
			boolean result = restTemplate.postForObject( SEND_MAIL_URL, 
					mail, Boolean.class);
			System.out.println("Send mail : " + result);
		} catch (RestClientException e) {
			System.out.println("Mail service is not available.");
		}
	}
	
	public List<String> addUsers(List<User> lu) {
		List<String> nameList = new ArrayList<>();
		for (User each:lu) {
			try {
				add(each);
			} catch(Exception e) {
				nameList.add(each.getName());
			}
		}
		return nameList;
	}
	
	public String updateUser(User usr) {
		if (usr.getName() == null) {
			throw new DataViolationException("User name must not be empty.");
		} else if (usr.getEmail() == null) {
			throw new DataViolationException("User email must not be empty.");
		} else if (!usr.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new DataViolationException("User email format is wrong.");
		}
		for (int i=0; i< userRecords.size(); i++) {
			User usrn = userRecords.get(i);
			if (usrn.getEmail().equals(usr.getEmail())) {
				userRecords.set(i, usr);
				return UPDATE_SUCCESS;
			}
		}
		return UPDATE_FAILURE;
	}
	
	public List<String> updateUsers(List<User> lu) {
		List<String> nameList = new ArrayList<>();
		for (User each:lu) {
			try {
				if (updateUser(each).equals(UPDATE_FAILURE)){
					nameList.add(each.getName());
				}
			} catch(Exception e) {
				nameList.add(each.getName());
			}
		}
		return nameList;
	}
	
	public String deleteUser(String email) {
		for(int i=0; i<userRecords.size(); i++) {
			User usrn = userRecords.get(i);
			if (usrn.getEmail().equals(email)) {
				userRecords.remove(i);
				RestTemplate restTemplate = new RestTemplate();
				Mail mail = new Mail();
				mail.setFromMail("no_reply@example.com");
				mail.setToMail(email);
				mail.setSubject("Delete user");
				mail.setBody("Delete user");
				try {
					boolean result = restTemplate.postForObject( SEND_MAIL_URL, 
							mail, Boolean.class);
			    	System.out.println("Send mail : " + result);
				} catch (RestClientException e) {
					System.out.println("Mail service is not available.");
				}
				return DELETE_SUCCESS;
			}
		}
		throw new UserNotFoundException("User is not found.");
	}
	
	public List<String> deleteUsers(List<String> emails) {
		List<String> emailList = new ArrayList<>();
		for (String each:emails) {
			try {
				deleteUser(each);
			} catch(Exception e) {
				emailList.add(each);
			}
		}
		return emailList;
	}
	
	public List<User> getUserRecords() {
		return userRecords;
	}

}

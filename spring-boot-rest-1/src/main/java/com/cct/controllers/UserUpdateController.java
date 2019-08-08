package com.cct.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cct.beans.User;
import com.cct.beans.UserRegistration;

@RestController
public class UserUpdateController {
	
	@RequestMapping(method = RequestMethod.PUT, value="/update/user")
	public String updateUserRecord(@RequestBody User usrn) {
		System.out.println("In updateUserRecord");   
	    return UserRegistration.getInstance().updateUser(usrn);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/update/users")
	public List<String> updateUserRecords(@RequestBody List<User> lu) {
		System.out.println("In updateUserRecords");   
	    return UserRegistration.getInstance().updateUsers(lu);
	}
}

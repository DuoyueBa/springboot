package com.cct.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cct.beans.User;
import com.cct.beans.UserRegistration;

@RestController
public class UserDeleteController {
	
	@RequestMapping(method = RequestMethod.DELETE, value="/delete/user/{email}")
	public String deleteUserRecord(@PathVariable("email") String email) {

		System.out.println("In deleteUserRecord");   

	    return UserRegistration.getInstance().deleteUser(email);

	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/delete/users")
	public List<String> deleteUserRecords(@RequestBody List<String> el) {
		System.out.println("In deleteUserRecords");   
	    return UserRegistration.getInstance().deleteUsers(el);
	}

}

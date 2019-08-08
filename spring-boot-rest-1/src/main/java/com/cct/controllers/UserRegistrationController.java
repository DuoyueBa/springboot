package com.cct.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cct.beans.User;
import com.cct.beans.UserRegistration;
import com.cct.beans.UserRegistrationReply;

@RestController
public class UserRegistrationController {
	
	@RequestMapping(method = RequestMethod.POST, value="/register/user")
	@ResponseStatus(HttpStatus.CREATED)
	public UserRegistrationReply registerUser(@RequestBody User user) {
		System.out.println("In registerUser");
		UserRegistrationReply usrregreply = new UserRegistrationReply();           
		
		UserRegistration.getInstance().add(user);
		
	    //We are setting the below value just to reply a message back to the caller
	    usrregreply.setName(user.getName());
	    usrregreply.setAge(user.getAge());
	    usrregreply.setEmail(user.getEmail());
	    usrregreply.setRegistrationStatus("Successful");

	    return usrregreply;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/register/users")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> registerUsers(@RequestBody List<User> lu) {
		System.out.println("In registerUsers");
		return UserRegistration.getInstance().addUsers(lu);
	}

}

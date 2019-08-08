package com.cct.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cct.beans.User;
import com.cct.beans.UserRegistration;

@RestController
public class UserRetrieveController {
	
	@RequestMapping(method = RequestMethod.GET, value="/user/alluser")
	public List<User> getAllUsers() {
		System.out.println("Get users");
		return UserRegistration.getInstance().getUserRecords();
	}

}

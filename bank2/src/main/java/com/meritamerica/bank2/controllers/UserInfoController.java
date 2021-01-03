package com.meritamerica.bank2.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.bank2.models.User;



@RestController
public class UserInfoController {
	
	List<User> newUser = new ArrayList<User>();
	
	
	@PostMapping(" /authenticate/createUse")
	public User create(@RequestBody @Valid User user) throws NoSuchAlgorithmException {
		newUser.add(user);
		return user;
	
    }

}

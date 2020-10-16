package com.transporterapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transporterapi.bean.User;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping("/{id}")
	public User getUserById() {
		return new User("am123","aman","indore","7869336577","ww.gogle.com","1932815");
	}
}

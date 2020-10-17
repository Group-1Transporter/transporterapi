package com.transporterapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.transporterapi.Service.UserService;
import com.transporterapi.bean.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") String id) {
		return userService.getUserById(id);
	}
	@PostMapping("")
	public User createNewUser(@RequestBody User user ) {
		return userService.createUser(user);
	}
}

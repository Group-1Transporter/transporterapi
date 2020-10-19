package com.transporterapi.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.transporterapi.Service.UserService;
import com.transporterapi.bean.User;
import com.transporterapi.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
		return userService.getUserById(id);
	}
	@PostMapping("/")
	public ResponseEntity<User> createNewUser(@RequestParam("file") MultipartFile file ,@RequestParam("user") User user)throws ResourceNotFoundException {
		if(file.isEmpty())
			return new ResourceNotFoundException("image not found");
		return userService.createUser(user,file);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUserById(@PathVariable("id") String id) {
		return userService.deleteUserById(id);
	}
	
	@GetMapping("")
	public ResponseEntity<ArrayList<User>> getAllUsers(){
		return userService.getAllUsers();
	}
}

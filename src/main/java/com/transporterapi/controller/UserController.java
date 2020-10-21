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
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<User> getUserById(@PathVariable("id") String id)throws ResourceNotFoundException, InterruptedException, ExecutionException {
		User user=userService.getUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<User> createNewUser(@RequestParam("file") MultipartFile file ,@RequestParam("name")String name,
			@RequestParam("address")String address,
			@RequestParam("contactNumber")String contactNumber,@RequestParam("token")String token)throws ResourceNotFoundException {
		if(file.isEmpty())
			throw new ResourceNotFoundException("image not found.");
		 User user=userService.createUser(new User("",name,address,contactNumber,"",token),file);
		 return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUserById(@PathVariable("id") String id)throws ResourceNotFoundException, InterruptedException, ExecutionException {
		User user=userService.deleteUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK) ;
	}
	
	@GetMapping("/")
	public ResponseEntity<ArrayList<User>> getAllUsers() throws InterruptedException, ExecutionException{
		ArrayList<User>al=userService.getAllUsers();
		return new ResponseEntity<ArrayList<User>>(al,HttpStatus.OK);
	}
}

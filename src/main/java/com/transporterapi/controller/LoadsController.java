package com.transporterapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transporterapi.Service.LoadsService;
import com.transporterapi.bean.Loads;

@RestController
public class LoadsController {
	
	@Autowired
	LoadsService loadsService;
	
	@PostMapping("/create")
	public Loads createLoads(@RequestBody Loads loads) {
		return loadsService.createLoads(loads);
	}
	
	@GetMapping("/all-load/{userId}")
	public ArrayList<Loads> getAllLoads(@PathVariable("userId") String id){		
		return loadsService.getAllLoads(id);
	}
	
	@GetMapping("/load/{leadId}")
	public Loads getLoadById(@PathVariable("leadId") String id) {
		return loadsService.getLoadById(id);
	}
	
	@GetMapping("/confirm-load/{userId}")
	public ArrayList<Loads> getConfirmLoads(@PathVariable("userId") String id){
		return loadsService.getConfirmLoads(id);
	}
	
	@GetMapping("/completed-load/{userId}")
	public ArrayList<Loads> getCompletedLoads(@PathVariable("userId") String id){
		return loadsService.getCompletedLoads(id);
	}
	
	@DeleteMapping("/load/{leadId}")
	public Loads deleteLoadById(@PathVariable("leadId") String id) {
		return loadsService.deleteLoadById(id);
	}
}

package com.transporterapi.controller;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transporterapi.bean.Vehicle;
import com.transporterapi.exception.ResourceNotFoundException;
import com.transporterapi.Service.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
	@Autowired
	VehicleService vehicleService;
	
	@PostMapping("/")
	public ResponseEntity<Vehicle> createVehicle(@RequestParam("name")String name,
			@RequestParam("count")int count,
			@RequestParam("transporterId")String id,
			@RequestParam("file")MultipartFile file) throws ResourceNotFoundException, IOException, InterruptedException, ExecutionException{
		if(file.isEmpty())
			throw new ResourceNotFoundException("image not found");
		Vehicle vehicle = new  Vehicle();
		vehicle.setName(name);
		vehicle.setCount(count);
		return new ResponseEntity<>(vehicleService.createVehicle(vehicle, id,file),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}/{transporterId}")
	public ResponseEntity<Vehicle> deleteVehicle(@PathVariable String id,
			@PathVariable String transporterId) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		Vehicle vehicle = vehicleService.deleteVehicle(id, transporterId);
		return new ResponseEntity<Vehicle>(vehicle,org.springframework.http.HttpStatus.OK);
	}
	
}
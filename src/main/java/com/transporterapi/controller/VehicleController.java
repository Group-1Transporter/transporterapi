package com.transporterapi.controller;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transporterapi.Service.VehicleService;
import com.transporterapi.bean.Vehicle;
import com.transporterapi.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
	@Autowired
	VehicleService vehicleService;
	
	@PostMapping("/")
	public ResponseEntity<Vehicle> createVehicle(@RequestParam("file") MultipartFile file ,@RequestParam("name")String name,
			@RequestParam("count")String count,@RequestParam("transporterId")String transporterId) throws IOException, ResourceNotFoundException{
		if(file.isEmpty())
			throw new ResourceNotFoundException("file not found.");
		Vehicle vehicle=new Vehicle();
		vehicle.setCount(count);
		vehicle.setName(name);
		vehicleService.createVehicle(vehicle, file,transporterId);		
		return new ResponseEntity<Vehicle>(vehicle,HttpStatus.OK);
	}
	@GetMapping("/{transporterId}")
	public ResponseEntity<ArrayList<Vehicle>> getAllVehicles(@PathVariable("transporterId") String id) throws InterruptedException, ExecutionException{
	ArrayList<Vehicle>al=vehicleService.getAllVehicles(id);
	return new ResponseEntity<ArrayList<Vehicle>>(al,HttpStatus.OK);
	}
	@DeleteMapping("/{transporterId}/{vehicleId}")
	public ResponseEntity<Vehicle> deleteVehicle(@PathVariable("transporterId") String transporterId,@PathVariable("vehicleId")String vehicleId) throws InterruptedException, ExecutionException, ResourceNotFoundException{
		Vehicle vehicle=vehicleService.deleteVehicle(transporterId, vehicleId);
		return new ResponseEntity<Vehicle>(vehicle,HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/update")
	public ResponseEntity<Vehicle> updateVehicle(@RequestParam("name")String name,
			@RequestParam("count")String count,@RequestParam("transporterId")String transporterId,@RequestParam("imageUrl")String imageUrl,@RequestParam("vehicleId")String vehicleId){
		Vehicle vehicle=new Vehicle();
		vehicle.setCount(count);
		vehicle.setImageUrl(imageUrl);
		vehicle.setName(name);
		vehicle.setVehicleId(vehicleId);
		vehicle=vehicleService.updateVehicle(vehicle, transporterId);
		return new ResponseEntity<Vehicle>(vehicle,HttpStatus.OK);
	}
	
	
	@PostMapping("/update/image")
	public ResponseEntity<Vehicle> updateImage(@RequestParam("file") MultipartFile file,@RequestParam("transporterId")String transporterId,@RequestParam("vehicleId")String vehicleId) throws IOException, ResourceNotFoundException, InterruptedException, ExecutionException{
		if(file.isEmpty())
			throw new ResourceNotFoundException("file not found.");
		Vehicle vehicle=vehicleService.updateImage(file, transporterId, vehicleId);
		if(vehicle!=null)
			return new ResponseEntity<Vehicle>(vehicle,HttpStatus.OK);
		else
			throw new ResourceNotFoundException("vehicle not found with id "+vehicleId);
	}
	
	
}

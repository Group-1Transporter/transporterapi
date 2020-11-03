package com.transporterapi.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.transporterapi.FileUtility;
import com.transporterapi.bean.Transporter;
import com.transporterapi.bean.Vehicle;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class VehicleService {

	Firestore firestore = FirestoreClient.getFirestore();
	
	//create new vehicle
	public Vehicle createVehicle(Vehicle vehicle,String transporterId,MultipartFile file) throws IOException, InterruptedException, ExecutionException {
		String imgUrl = new FileUtility().uploadFile(file);
		String vehicelId = firestore.collection("Transporter").document().getId();
		vehicle.setVehicelId(vehicelId);
		vehicle.setImgUrl(imgUrl);
		Transporter transporter = firestore.collection("Transporter").document(transporterId).get().get().toObject(Transporter.class);
		ArrayList<Vehicle>vehicleList = transporter.getVehicleList();
		if(vehicleList==null)
			vehicleList = new ArrayList<>();
		vehicleList.add(vehicle);
		transporter.setVehicleList(vehicleList);
		firestore.collection("Transporter").document(transporterId).set(transporter);		
		return vehicle;
	}
	
	//delete vehicle
	public Vehicle deleteVehicle(String vehicleId,String transporterId) throws InterruptedException, ExecutionException {
			Transporter t = firestore.collection("Transporter").document(transporterId).get().get().toObject(Transporter.class);
			ArrayList<Vehicle> vehicleList = t.getVehicleList();
			Vehicle vehicle = null;
			for(Vehicle v : vehicleList) {
				if(v.getVehicelId().equals(vehicleId))
					vehicle = v;
					vehicleList.remove(v);
			}
			firestore.collection("Transporter").document(transporterId).set(t);
			return vehicle;
	}
}
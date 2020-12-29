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

	
	
	//create new vehicle
	public Transporter createVehicle(Vehicle vehicle,String transporterId,MultipartFile file) throws IOException, InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();
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
		return transporter;
	}
	
	//delete vehicle
	public Transporter deleteVehicle(String vehicleId,String transporterId) throws InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();
		Transporter t = firestore.collection("Transporter").document(transporterId).get().get().toObject(Transporter.class);
			ArrayList<Vehicle> vehicleList = t.getVehicleList();
			Vehicle vehicle = null;
			for(Vehicle v : vehicleList) {
				if(v.getVehicelId().equals(vehicleId))
					vehicle = v;
					vehicleList.remove(v);
			}
			t.setVehicleList(vehicleList);
			firestore.collection("Transporter").document(transporterId).set(t);
			return t;
	}

	public Transporter updateVehicle(Transporter transporter) throws InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();
		firestore.collection("Transporter").document(transporter.getTransporterId()).set(transporter);
		return transporter;
	}

	public Transporter updateImage(MultipartFile file, String transporterId, String id) throws IOException, InterruptedException, ExecutionException {
		Firestore firestore = FirestoreClient.getFirestore();
		String imgUrl = new FileUtility().uploadFile(file);
		Transporter transporter=firestore.collection("Transporter").document(transporterId).get().get().toObject(Transporter.class);
		ArrayList<Vehicle>al=transporter.getVehicleList();
		int i=0;
		for(i=0;i<al.size();i++) {
			Vehicle v=al.get(i);
			if(v.getVehicelId().equals(id)) {
				v.setImgUrl(imgUrl);
				al.set(i,v);
				break;
			}
		}
		if(i==al.size()) {
			transporter=null;
			return transporter;
		}
		transporter.setVehicleList(al);
		firestore.collection("Transporter").document(transporter.getTransporterId()).set(transporter);
		return transporter;
	}
}
package com.transporterapi.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.transporterapi.FileUtility;
import com.transporterapi.bean.Leads;
import com.transporterapi.bean.User;
import com.transporterapi.bean.Vehicle;
import com.transporterapi.exception.ResourceNotFoundException;

@Service
public class VehicleService {
	
	public Vehicle createVehicle(Vehicle vehicle,MultipartFile file,String transporterId) throws IOException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./serviceAccountKey.json");
   	    Storage storage =  StorageOptions.newBuilder().setProjectId("transportapi-23367").
  	    		setCredentials(GoogleCredentials.fromStream(serviceAccount)).build().getService();
  
   	    HashMap<String, String> hm = new HashMap<>();
   	    
   	    hm.put("firebaseStorageDownloadTokens", "3434434343434dfdf");
   	    
   	    BlobId blobId = BlobId.of("transportapi-23367.appspot.com",file.getOriginalFilename());
	   
   	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).
	    		setContentType("image/jpeg")
	    		.setMetadata(hm)
	    		.build();
	    
	    File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos;
		
			fos = new FileOutputStream(convertedFile);
			
				fos.write(file.getBytes());
				fos.close();
				Blob blob =  storage.create(blobInfo, Files.readAllBytes(convertedFile.toPath()));
		        
		        com.google.cloud.storage.Bucket bucket= StorageClient.getInstance().bucket("transportapi-23367.appspot.com");
		        String location = bucket.getSelfLink();
		        String bname = bucket.getName();
			    String imageUrl = "https://firebasestorage.googleapis.com/v0/b/transportapi-23367.appspot.com/o/"+convertedFile+"?alt=media&token=3434434343434dfdf";
		        System.out.println("Image Url : "+imageUrl);
		        vehicle.setImageUrl(imageUrl);
		        vehicle.setVehicleId(dbFirestore.collection("Users").document().getId());		        		        
				dbFirestore.collection("Transporter").document(transporterId).collection("Vehicles").document(vehicle.getVehicleId()).set(vehicle);
		        return vehicle;
	}
	public ArrayList<Vehicle> getAllVehicles(String id) throws InterruptedException, ExecutionException{
		ArrayList<Vehicle>al=new ArrayList<Vehicle>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection("Transporter").document(id).collection("Vehicles").get();
		List<QueryDocumentSnapshot> documents;
		documents = future.get().getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			al.add(document.toObject(Vehicle.class));
		}
		return al;
	}
	public Vehicle deleteVehicle(String transporterId,String vehicleId) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Vehicle vehicle=dbFirestore.collection("Transporter").document(transporterId).collection("Vehicles").document(vehicleId).get().get().toObject(Vehicle.class);
		if(vehicle!=null) {
				dbFirestore.collection("Transporter").document(transporterId).collection("Vehicles").document(vehicleId).delete();
				return vehicle;
			}
			else
				throw new ResourceNotFoundException("Vehicle not found with id "+vehicleId);
		
	}
	public Vehicle updateVehicle(Vehicle vehicle,String transporterId) {
		Firestore dbFirestore = FirestoreClient.getFirestore();		        		        
		dbFirestore.collection("Transporter").document(transporterId).collection("Vehicles").document(vehicle.getVehicleId()).set(vehicle);
		return vehicle;        
	}
	public Vehicle updateImage(MultipartFile file,String transporterId,String vehicleId) throws IOException, InterruptedException, ExecutionException {		
		Firestore dbFirestore = FirestoreClient.getFirestore();
		String imageUrl=new FileUtility().uploadFile(file);
		dbFirestore.collection("Transporter").document(transporterId).collection("Vehicles").document(vehicleId).update("imageUrl", imageUrl);
		Vehicle vehicle=dbFirestore.collection("Transporter").document(transporterId).collection("Vehicles").document(vehicleId).get().get().toObject(Vehicle.class);
		return vehicle;
	}
}

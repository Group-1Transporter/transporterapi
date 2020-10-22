package com.transporterapi.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import com.google.cloud.firestore.DocumentReference;
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
import com.transporterapi.bean.User;
import com.transporterapi.exception.ResourceNotFoundException;

	
@Service
public class UserService {
	public static final String COL_NAME="Users";
	public User createUser(User user,MultipartFile file) {
		try {
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
				user.setImageUrl(imageUrl);
				Firestore dbFirestore = FirestoreClient.getFirestore();
				user.setUserId(dbFirestore.collection(COL_NAME).document().getId());
				dbFirestore.collection(COL_NAME).document(user.getUserId()).set(user);
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		return user;
	}
	public User getUserById(String id)throws ResourceNotFoundException, InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        User user;
        
        
			DocumentSnapshot document = future.get();
			if(document.exists()) {
				user=document.toObject(User.class);				
				return user;
			}
			
       
        throw new ResourceNotFoundException("user not found with id "+id);
	}
	public User deleteUserById(String id)throws ResourceNotFoundException, InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference =dbFirestore.collection(COL_NAME).document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		User user;
		
			DocumentSnapshot document = future.get();
			if(document.exists()) {
				user=document.toObject(User.class);
				dbFirestore.collection(COL_NAME).document(id).delete();
				return user;
			}
		
		throw new ResourceNotFoundException("user not found with id "+id);
	}
	
	public ArrayList<User> getAllUsers() throws InterruptedException, ExecutionException{
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ArrayList<User> al=new ArrayList<User>();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).get();
		List<QueryDocumentSnapshot> documents;
		
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   al.add(document.toObject(User.class));
				}
				
		return al;
	}
}

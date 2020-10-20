package com.transporterapi.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.transporterapi.bean.Bid;
import com.transporterapi.bean.Loads;
import com.transporterapi.bean.User;
import com.transporterapi.exception.ResourceNotFoundException;

import io.grpc.internal.Http2ClientStreamTransportState;

@Service
public class LoadsService {
	public static final String COL_NAME="Loads";
	public ResponseEntity<Loads> createLoads(Loads loads) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		loads.setLeadId(dbFirestore.collection(COL_NAME).document().getId());
		dbFirestore.collection(COL_NAME).document(loads.getLeadId()).set(loads);
		return new ResponseEntity<Loads>(loads,HttpStatus.OK);
	}
	
	
	public ResponseEntity<ArrayList<Loads>> getAllLoads(String id){
		ArrayList<Loads>al=new ArrayList<Loads>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("userId",id).get();
		List<QueryDocumentSnapshot> documents;
		try {
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   al.add(document.toObject(Loads.class));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return new ResponseEntity<ArrayList<Loads>>(al,HttpStatus.OK);
	}
	public ResponseEntity<Loads> getLoadById(String id)throws ResourceNotFoundException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        Loads loads;        
        try {
			DocumentSnapshot document = future.get();
			if(document.exists()) {
				loads=document.toObject(Loads.class);				
				return new ResponseEntity<Loads>(loads,HttpStatus.OK);
			}
			
        }catch(Exception e) {
        }
        throw new ResourceNotFoundException("load not found with id "+id);
	}
	public ResponseEntity<ArrayList<Loads>> getConfirmLoads(String id){
		ArrayList<Loads>al=new ArrayList<Loads>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("userId",id).get();
		List<QueryDocumentSnapshot> documents;
		try {
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   Loads load=document.toObject(Loads.class);
				   if(!load.getStatus().equalsIgnoreCase("Completed")) {
					   al.add(load);
				   }
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return new ResponseEntity<ArrayList<Loads>>(al,HttpStatus.OK);
	}
	public ResponseEntity<ArrayList<Loads>> getCompletedLoads(String id){
		ArrayList<Loads>al=new ArrayList<Loads>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("userId",id).get();
		List<QueryDocumentSnapshot> documents;
		try {
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   Loads load=document.toObject(Loads.class);
				   if(load.getStatus().equalsIgnoreCase("Completed")) {
					   al.add(load);
				   }
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		return new ResponseEntity<ArrayList<Loads>>(al,HttpStatus.OK);
	}
	
	public ResponseEntity<Loads> deleteLoadById(String id)throws ResourceNotFoundException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference =dbFirestore.collection(COL_NAME).document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		Loads load;
		try {
			DocumentSnapshot document = future.get();
			if(document.exists()) {
				load=document.toObject(Loads.class);
				dbFirestore.collection(COL_NAME).document(id).delete();
				return new ResponseEntity<Loads>(load,HttpStatus.OK);
			}
		}catch(Exception e) {
			
		}
		throw new ResourceNotFoundException("load not found with id "+id);
		
	}
}

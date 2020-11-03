package com.transporterapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import com.transporterapi.bean.Leads;
import com.transporterapi.bean.User;
import com.transporterapi.exception.ResourceNotFoundException;

import io.grpc.internal.Http2ClientStreamTransportState;

@Service
public class LeadsService {
	public static final String COL_NAME="Leads";
	
	public Leads createLeads(Leads leads) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		leads.setLeadId(dbFirestore.collection("Leads").document().getId());
		dbFirestore.collection(COL_NAME).document(leads.getLeadId()).set(leads);
		return leads;
	}
	
	public Leads updateLeads(Leads leads) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		dbFirestore.collection(COL_NAME).document(leads.getLeadId()).set(leads);
		return leads;
	}
	
	
	public ArrayList<Leads> getAllLeads(String id) throws InterruptedException, ExecutionException{
		ArrayList<Leads>al=new ArrayList<Leads>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("userId",id).get();
		List<QueryDocumentSnapshot> documents;
		
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   al.add(document.toObject(Leads.class));
				}
				
		return al;
	}
	public Leads getLeadById(String id) throws InterruptedException, ExecutionException{
		Firestore dbFirestore = FirestoreClient.getFirestore();		
		Leads leads=dbFirestore.collection(COL_NAME).document(id).get().get().toObject(Leads.class);       
        return leads;
	}
	public ArrayList<Leads> getConfirmLeads(String id) throws InterruptedException, ExecutionException{
		ArrayList<Leads>al=new ArrayList<Leads>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("userId",id).get();
		List<QueryDocumentSnapshot> documents;
		
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   Leads lead=document.toObject(Leads.class);
				   if(!lead.getStatus().equalsIgnoreCase("Completed")) {
					   al.add(lead);
				   }
				}
		 		
		return al;
	}
	public ArrayList<Leads> getCompletedLeads(String id) throws InterruptedException, ExecutionException{
		ArrayList<Leads>al=new ArrayList<Leads>();
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("userId",id).get();
		List<QueryDocumentSnapshot> documents;
		
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   Leads lead=document.toObject(Leads.class);
				   if(lead.getStatus().equalsIgnoreCase("Completed")) {
					   al.add(lead);
				   }
				}
				
		return al;
	}
	
	public Leads deleteLeadById(String id)throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Leads leads=dbFirestore.collection(COL_NAME).document(id).get().get().toObject(Leads.class);       
		dbFirestore.collection(COL_NAME).document(id).delete();
        return leads;		
	}	
}

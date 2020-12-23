package com.transporterapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.transporterapi.bean.Bid;
import com.transporterapi.bean.State;
import com.transporterapi.exception.ResourceNotFoundException;

@Service
public class StateService {
	
	Firestore fireStore = FirestoreClient.getFirestore();
	public ArrayList<State> getStateList() throws InterruptedException, ExecutionException {
		
		ArrayList<State> al = new ArrayList<>();
		ApiFuture<QuerySnapshot> apiFuture = fireStore.collection("State").get();
		QuerySnapshot querySnapshot = apiFuture.get();
		List<QueryDocumentSnapshot> documentSnapshotList = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documentSnapshotList) {
			State state = document.toObject(State.class);
			al.add(state);
		}
		return al;
	}
    
	public State saveState(State s) throws Exception {
		String stateId=fireStore.collection("State").document().getId().toString();
	    s.setStateId(stateId);
		fireStore.collection("State").document(stateId).set(s);
		return s;
	}
	  public State getStateById(String stateId) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		  
		  State state = fireStore.collection("State").document(stateId).get().get().toObject(State.class);
	      if (state!= null) 
	    	  return state;
	      else
	    	  throw new ResourceNotFoundException("state not found for this id "+stateId);
	    	  
	  }



	    

}
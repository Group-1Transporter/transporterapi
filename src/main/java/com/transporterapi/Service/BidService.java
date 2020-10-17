package com.transporterapi.Service;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.transporterapi.bean.Bid;
import com.transporterapi.bean.User;

@Service
public class BidService {
	
	public static final String COL_NAME="Bids";
	
	public Bid createBid(Bid bid) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		bid.setBidId(dbFirestore.collection(COL_NAME).document().getId());
		dbFirestore.collection(COL_NAME).document(bid.getBidId()).set(bid);
		return bid;
	}
	
	public Bid getBidById(String id) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference =dbFirestore.collection(COL_NAME).document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		Bid bid;
		try {
			DocumentSnapshot document = future.get();
			if(document.exists()) {
				bid=document.toObject(Bid.class);
				return bid;
			}
		}catch(Exception e) {
			
		}
		return null;
	}
}

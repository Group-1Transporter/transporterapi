package com.transporterapi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.transporterapi.bean.Bid;
import com.transporterapi.bean.Leads;
import com.transporterapi.exception.ResourceNotFoundException;

@Service
public class BidService {
	
	public static final String COL_NAME="Bids";
	
	public Bid createBid(Bid bid) throws ResourceNotFoundException, InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		bid.setBidId(dbFirestore.collection(COL_NAME).document().getId());
		LeadsService leadsService=new LeadsService();
		dbFirestore.collection(COL_NAME).document(bid.getBidId()).set(bid);
		Leads leads=leadsService.getLeadById(bid.getLeadId());
		int count =Integer.parseInt(leads.getBidCount());
		count++;
		leads.setBidCount(count+"");
		leadsService.updateLeads(leads);
		return bid;
	}
	
	public Bid  deleteBidById(String id) throws ResourceNotFoundException, InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference =dbFirestore.collection(COL_NAME).document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		Bid bid;
		
			DocumentSnapshot document = future.get();
			if(document.exists()) {
				bid=document.toObject(Bid.class);
				dbFirestore.collection(COL_NAME).document(id).delete();
				LeadsService leadsService=new LeadsService();
				Leads leads=leadsService.getLeadById(bid.getLeadId());
				int count =Integer.parseInt(leads.getBidCount());
				count--;
				leads.setBidCount(count+"");
				leadsService.updateLeads(leads);
				return bid;
			}
			else
				throw new ResourceNotFoundException("bid not not found with id "+id);
	}
	public  ArrayList<Bid> getAllBidsByLeadId(String id) throws InterruptedException, ExecutionException{
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ArrayList<Bid> al=new ArrayList<Bid>();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("leadId",id).get();
		List<QueryDocumentSnapshot> documents;
		
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   al.add(document.toObject(Bid.class));
				}
				
		return al;
	}

	public ArrayList<Bid> getAllBids(String id) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ArrayList<Bid> al=new ArrayList<Bid>();
		ApiFuture<QuerySnapshot> future =dbFirestore.collection(COL_NAME).whereEqualTo("transporterId",id).get();
		List<QueryDocumentSnapshot> documents;
		
			documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				   al.add(document.toObject(Bid.class));
				}
				
		return al;
	}
}

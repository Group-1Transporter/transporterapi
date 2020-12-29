package com.transporterapi.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.transporterapi.FileUtility;
import com.transporterapi.bean.Transporter;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class TransporterService {

	private static final String TAG = "Transporter";
	
	//get all transporter
	public ArrayList<Transporter> getTransporter() throws InterruptedException, ExecutionException{
		Firestore fireStore = FirestoreClient.getFirestore();
		ArrayList<Transporter>transporterList = new ArrayList<Transporter>();
		List<QueryDocumentSnapshot> list = fireStore.collection(TAG).get().get().getDocuments();	
		for(QueryDocumentSnapshot queryDocument : list ) {
			transporterList.add(queryDocument.toObject(Transporter.class));
		}
		return transporterList;		
	}
	
	
	//get Single Transporter
	public Transporter getTransporter(String id) throws InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		Transporter t = fireStore.collection(TAG).document(id).get().get().toObject(Transporter.class);
		return t;
	}
	
	// create new transporter
	public Transporter createTransporter(Transporter transporter,MultipartFile file) throws IOException {		
		Firestore fireStore = FirestoreClient.getFirestore();
		String imageUrl = new FileUtility().uploadFile(file);
		transporter.setImageUrl(imageUrl);
		fireStore.collection(TAG).document(transporter.getTransporterId()).set(transporter);
		return transporter;
	}
	
	//delete Transporter by id
	public Transporter deleteTransporter(String id) throws InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		Transporter t = fireStore.collection(TAG).document(id).get().get().toObject(Transporter.class);
		fireStore.collection(TAG).document(id).delete();
			return t;
	}
	
	//update transporter details without image
	public Transporter updateTransporter(Transporter transporter) throws InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		fireStore.collection(TAG).document(transporter.getTransporterId()).set(transporter);
		return transporter;
	}
	
	//update transporter image only
	public Transporter updateTransporter(String transporterId,MultipartFile file) throws IOException, InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		String imageUrl = new FileUtility().uploadFile(file);
		Transporter t = fireStore.collection(TAG).document(transporterId).get().get().toObject(Transporter.class);
		t.setImageUrl(imageUrl);
		fireStore.collection(TAG).document(transporterId).set(t);
		return t;
		
		
	}
	
}
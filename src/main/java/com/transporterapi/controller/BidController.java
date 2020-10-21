package com.transporterapi.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transporterapi.Service.BidService;
import com.transporterapi.bean.Bid;
import com.transporterapi.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/bid")
public class BidController {
	
	@Autowired
	BidService bidService;
	
	@PostMapping("/")
	public ResponseEntity<Bid> createBid(@RequestBody Bid bid) throws ResourceNotFoundException, InterruptedException, ExecutionException {
		bid=bidService.createBid(bid);
		return new ResponseEntity<Bid>(bid,HttpStatus.OK);
	}
	
	@DeleteMapping("/{bidId}")
	public ResponseEntity<Bid> deleteBidById(@PathVariable("bidId") String id) throws ResourceNotFoundException, InterruptedException, ExecutionException {
		Bid bid=bidService.deleteBidById(id);
		return new ResponseEntity<Bid>(bid,HttpStatus.OK);
	}

	@GetMapping("/{leadId}")
	public ResponseEntity<ArrayList<Bid>> getBidById(@PathVariable("leadId") String id) throws InterruptedException, ExecutionException {		
		ArrayList<Bid>al=bidService.getAllBidsByLeadId(id);
		return new ResponseEntity<ArrayList<Bid>>(al,HttpStatus.OK) ;
	}
	@GetMapping("/all-bid/{transporterId}")
	public ResponseEntity<ArrayList<Bid>> getAllBids(@PathVariable("transporterId") String id) throws InterruptedException, ExecutionException {	
		ArrayList<Bid>al=bidService.getAllBids(id);
		return  new ResponseEntity<ArrayList<Bid>>(al,HttpStatus.OK);
	}
}

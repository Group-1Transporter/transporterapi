package com.transporterapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transporterapi.Service.BidService;
import com.transporterapi.bean.Bid;
import com.transporterapi.exception.ResourceNotFoundException;

@RestController
public class BidController {
	
	@Autowired
	BidService bidService;
	
	@PostMapping("/bid")
	public ResponseEntity<Bid> createBid(@RequestBody Bid bid) {
		return bidService.createBid(bid);
	}
	
	@DeleteMapping("/bid/{bidId}")
	public ResponseEntity<Bid> deleteBidById(@PathVariable("bidId") String id)throws ResourceNotFoundException {
		return bidService.deleteBidById(id);
	}

	@GetMapping("/bid/{leadId}")
	public ResponseEntity<ArrayList<Bid>> getBidById(@PathVariable("leadId") String id) {		
		return bidService.getAllBidsByLeadId(id);
	}
	@GetMapping("/all-bid/{transporterId}")
	public ResponseEntity<ArrayList<Bid>> getAllBids(@PathVariable("transporterId") String id) {		
		return bidService.getAllBids(id);
	}
}

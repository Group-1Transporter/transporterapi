package com.transporterapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transporterapi.Service.BidService;
import com.transporterapi.bean.Bid;

@RestController
public class BidController {
	
	@Autowired
	BidService bidService;
	
	@PostMapping("/bid")
	public Bid createBid(@RequestBody Bid bid) {
		return bidService.createBid(bid);
	}
	
	@GetMapping("/bid/{bidId}")
	public Bid getBidById(@PathVariable("bidId") String id) {
		return bidService.getBidById(id);
	}

}

package com.transporterapi.bean;

import java.util.*;

public class Transporter {
	private String transporterId,type,name,image,contactNumber,address,aadharCardNumber,gstNo,rating,token;
	private ArrayList<Vehicle>al;
	

	public Transporter(String transporterId, String type, String name, String image, String contactNumber,
			String address, String aadharCardNumber, String gstNo, String rating, String token, ArrayList<Vehicle> al) {
		super();
		this.transporterId = transporterId;
		this.type = type;
		this.name = name;
		this.image = image;
		this.contactNumber = contactNumber;
		this.address = address;
		this.aadharCardNumber = aadharCardNumber;
		this.gstNo = gstNo;
		this.rating = rating;
		this.token = token;
		this.al = al;
	}


	public Transporter() {
		super();
	}
	
	
	public ArrayList<Vehicle> getAl() {
		return al;
	}

	public void setAl(ArrayList<Vehicle> al) {
		this.al = al;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTransporterId() {
		return transporterId;
	}

	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAadharCardNumber() {
		return aadharCardNumber;
	}

	public void setAadharCardNumber(String aadharCardNumber) {
		this.aadharCardNumber = aadharCardNumber;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
}

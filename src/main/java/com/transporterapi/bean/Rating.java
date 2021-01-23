package com.transporterapi.bean;

public class Rating {
	private String rating;
	private String feedback;
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Rating(String rating, String feedback) {
		super();
		this.rating = rating;
		this.feedback = feedback;
	}
	public Rating() {}
	
}

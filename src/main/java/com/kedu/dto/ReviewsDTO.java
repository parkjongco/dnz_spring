package com.kedu.dto;

import java.sql.Timestamp;

public class ReviewsDTO {
	private int id;
	private int businessId;
	private int customerId;
	private int rating;
	private String comment;
	private Timestamp createdAt;
	 
	 
	public ReviewsDTO() {}


	public ReviewsDTO(int id, int businessId, int customerId, int rating, String comment, Timestamp createdAt) {
		super();
		this.id = id;
		this.businessId = businessId;
		this.customerId = customerId;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getBusinessId() {
		return businessId;
	}


	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	

}

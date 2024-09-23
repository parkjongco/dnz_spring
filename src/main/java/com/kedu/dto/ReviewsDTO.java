package com.kedu.dto;

import java.sql.Timestamp;

public class ReviewsDTO {
	private int reviewId;
	private int storeSeq;
	private String userId;
	private int reservationId;
	private int rating;
	private String reviewText;
	private Timestamp createdAt;
	private String storeName;
	
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getStoreSeq() {
		return storeSeq;
	}
	public void setStoreSeq(int storeSeq) {
		this.storeSeq = storeSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getStoreName() {
		return storeName;
	}
	
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public ReviewsDTO() {}
	
	public ReviewsDTO(int reviewId, int storeSeq, String userId, int reservationId, int rating, String reviewText,
			Timestamp createdAt, String storeName) {
		super();
		this.reviewId = reviewId;
		this.storeSeq = storeSeq;
		this.userId = userId;
		this.reservationId = reservationId;
		this.rating = rating;
		this.reviewText = reviewText;
		this.createdAt = createdAt;
		this.storeName = storeName;
	}
}

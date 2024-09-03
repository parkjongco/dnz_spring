package com.kedu.dto;

import java.sql.Timestamp;

public class ReservationDTO {
	private int reservationId;
    private int userId;
    private int restaurantId;
    private Timestamp reservationTime;
    private int numGuests;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    
	public ReservationDTO() {}


	public ReservationDTO(int reservationId, int userId, int restaurantId, Timestamp reservationTime, int numGuests,
			String status, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.reservationId = reservationId;
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.reservationTime = reservationTime;
		this.numGuests = numGuests;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}


	public int getReservationId() {
		return reservationId;
	}


	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}


	public Timestamp getReservationTime() {
		return reservationTime;
	}


	public void setReservationTime(Timestamp reservationTime) {
		this.reservationTime = reservationTime;
	}


	public int getNumGuests() {
		return numGuests;
	}


	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}


	public Timestamp getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
    
}
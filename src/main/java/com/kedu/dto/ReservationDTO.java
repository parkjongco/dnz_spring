package com.kedu.dto;

import java.sql.Timestamp;

public class ReservationDTO {
	private int reservationId;
    private String userId;
    private int storeSeq;
    private Timestamp reservationDate;
    private String reservationTime;
    private int numGuests;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String storeName;
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStoreSeq() {
		return storeSeq;
	}
	public void setStoreSeq(int storeSeq) {
		this.storeSeq = storeSeq;
	}
	public Timestamp getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Timestamp reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(String reservationTime) {
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
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public ReservationDTO(int reservationId, String userId, int storeSeq, Timestamp reservationDate,
			String reservationTime, int numGuests, String status, Timestamp createdAt, Timestamp updatedAt, String storeName) {
		super();
		this.reservationId = reservationId;
		this.userId = userId;
		this.storeSeq = storeSeq;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.numGuests = numGuests;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.storeName = storeName;
	}

    public ReservationDTO() {
    	
    }


    
}
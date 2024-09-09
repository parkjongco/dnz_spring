package com.kedu.dto;

import java.sql.Timestamp;

public class StoreDTO {

	private int storeSeq;
	private int memberId;
	private String category;
	private String name;
	private String description;
	private int seat_status;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	private String address;
	private int seatCapacity;


	
	

	public StoreDTO(int storeSeq, int memberId, String category, String name, String description, int seat_status,
			Timestamp createdAt, Timestamp modifiedAt, String address, int seatCapacity) {
		super();
		this.storeSeq = storeSeq;
		this.memberId = memberId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.seat_status = seat_status;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.address = address;
		this.seatCapacity = seatCapacity;
	}





	public int getStoreSeq() {
		return storeSeq;
	}





	public void setStoreSeq(int storeSeq) {
		this.storeSeq = storeSeq;
	}





	public int getMemberId() {
		return memberId;
	}





	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}





	public String getCategory() {
		return category;
	}





	public void setCategory(String category) {
		this.category = category;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public int getSeat_status() {
		return seat_status;
	}





	public void setSeat_status(int seat_status) {
		this.seat_status = seat_status;
	}





	public Timestamp getCreatedAt() {
		return createdAt;
	}





	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}





	public Timestamp getModifiedAt() {
		return modifiedAt;
	}





	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}





	public String getAddress() {
		return address;
	}





	public void setAddress(String address) {
		this.address = address;
	}





	public int getSeatCapacity() {
		return seatCapacity;
	}





	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}





	public StoreDTO() {}


	

}
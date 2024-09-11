package com.kedu.dto;

import java.sql.Timestamp;

public class StoreDTO {

	private int storeSeq;
	private String memberId;
	private String category;
	private String name;
	private String description;
	private int seatStatus;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	private String address1;
	private String address2;
	private String postcode;
	private int seatCapacity;
	public int getStoreSeq() {
		return storeSeq;
	}
	public void setStoreSeq(int storeSeq) {
		this.storeSeq = storeSeq;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
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
	public int getSeatStatus() {
		return seatStatus;
	}
	public void setSeatStatus(int seatStatus) {
		this.seatStatus = seatStatus;
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
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public int getSeatCapacity() {
		return seatCapacity;
	}
	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	public StoreDTO(int storeSeq, String memberId, String category, String name, String description, int seatStatus,
			Timestamp createdAt, Timestamp modifiedAt, String address1, String address2, String postcode,
			int seatCapacity) {
		super();
		this.storeSeq = storeSeq;
		this.memberId = memberId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.seatStatus = seatStatus;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.address1 = address1;
		this.address2 = address2;
		this.postcode = postcode;
		this.seatCapacity = seatCapacity;
	}
	
	


	

}
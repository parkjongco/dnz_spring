package com.kedu.dto;

import java.sql.Timestamp;

public class StoreDTO {

	private int storeSeq;
	private int memberId;
	private String category;
	private String name;
	private String description;
	private int maxWaitingCount;
	private Timestamp createdAt;
	private Timestamp modifiedAt;


	public StoreDTO() {}


	public StoreDTO(int storeSeq, int memberId, String category, String name, String description, int maxWaitingCount,
			Timestamp createdAt, Timestamp modifiedAt) {
		super();
		this.storeSeq = storeSeq;
		this.memberId = memberId;
		this.category = category;
		this.name = name;
		this.description = description;
		this.maxWaitingCount = maxWaitingCount;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
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


	public int getMaxWaitingCount() {
		return maxWaitingCount;
	}


	public void setMaxWaitingCount(int maxWaitingCount) {
		this.maxWaitingCount = maxWaitingCount;
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


}
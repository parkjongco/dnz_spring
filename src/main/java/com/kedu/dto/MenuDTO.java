package com.kedu.dto;

import java.sql.Timestamp;

public class MenuDTO {
	private int menuSeq;
    private String name;
    private String description;
    private int price;
    private int storeId;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    
    
	public MenuDTO() {}


	public MenuDTO(int menuSeq, String name, String description, int price, int storeId, Timestamp createdAt,
			Timestamp modifiedAt) {
		super();
		this.menuSeq = menuSeq;
		this.name = name;
		this.description = description;
		this.price = price;
		this.storeId = storeId;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}


	public int getMenuSeq() {
		return menuSeq;
	}


	public void setMenuSeq(int menuSeq) {
		this.menuSeq = menuSeq;
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


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getStoreId() {
		return storeId;
	}


	public void setStoreId(int storeId) {
		this.storeId = storeId;
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

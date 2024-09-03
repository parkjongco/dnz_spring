package com.kedu.dto;

public class MenusDTO {
	private int id;
    private int businessId;
    private String name;
    private String description;
    private double price;
    
    
	public MenusDTO() {}


	public MenusDTO(int id, int businessId, String name, String description, double price) {
		super();
		this.id = id;
		this.businessId = businessId;
		this.name = name;
		this.description = description;
		this.price = price;
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


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	

}

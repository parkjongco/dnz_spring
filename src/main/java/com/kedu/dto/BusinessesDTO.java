package com.kedu.dto;

public class BusinessesDTO {
	private int id;
    private String name;
    private String description;
    private int locationId;
    
    
	public BusinessesDTO() {}


	public BusinessesDTO(int id, String name, String description, int locationId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.locationId = locationId;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public int getLocationId() {
		return locationId;
	}


	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
    
}
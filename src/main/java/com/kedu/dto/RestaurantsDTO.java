package com.kedu.dto;

public class RestaurantsDTO {
	private int restaurantId;
	private String name;
	private String location;
	private String description;
	private int totalTables;
	 
	public RestaurantsDTO() {}

	public RestaurantsDTO(int restaurantId, String name, String location, String description, int totalTables) {
		super();
		this.restaurantId = restaurantId;
		this.name = name;
		this.location = location;
		this.description = description;
		this.totalTables = totalTables;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalTables() {
		return totalTables;
	}

	public void setTotalTables(int totalTables) {
		this.totalTables = totalTables;
	}
	

}
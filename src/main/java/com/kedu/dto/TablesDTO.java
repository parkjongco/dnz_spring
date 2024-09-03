package com.kedu.dto;

public class TablesDTO {
	private int tableId;
	private int restaurantId;
	private int tableNumber;
	private int capacity;
	private String available;


	public TablesDTO() {}


	public TablesDTO(int tableId, int restaurantId, int tableNumber, int capacity, String available) {
		super();
		this.tableId = tableId;
		this.restaurantId = restaurantId;
		this.tableNumber = tableNumber;
		this.capacity = capacity;
		this.available = available;
	}


	public int getTableId() {
		return tableId;
	}


	public void setTableId(int tableId) {
		this.tableId = tableId;
	}


	public int getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}


	public int getTableNumber() {
		return tableNumber;
	}


	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public String getAvailable() {
		return available;
	}


	public void setAvailable(String available) {
		this.available = available;
	}


}

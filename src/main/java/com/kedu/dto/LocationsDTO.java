package com.kedu.dto;

public class LocationsDTO {
	
	private int id;
    private String address;
    private double lat;
    private double lng;
    private String nearestStation1;
    private int distanceToStation1;
    private String nearestStation2;
    private int distanceToStation2;
    
   
	public LocationsDTO() {}


	public LocationsDTO(int id, String address, double lat, double lng, String nearestStation1, int distanceToStation1,
			String nearestStation2, int distanceToStation2) {
		super();
		this.id = id;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
		this.nearestStation1 = nearestStation1;
		this.distanceToStation1 = distanceToStation1;
		this.nearestStation2 = nearestStation2;
		this.distanceToStation2 = distanceToStation2;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}


	public String getNearestStation1() {
		return nearestStation1;
	}


	public void setNearestStation1(String nearestStation1) {
		this.nearestStation1 = nearestStation1;
	}


	public int getDistanceToStation1() {
		return distanceToStation1;
	}


	public void setDistanceToStation1(int distanceToStation1) {
		this.distanceToStation1 = distanceToStation1;
	}


	public String getNearestStation2() {
		return nearestStation2;
	}


	public void setNearestStation2(String nearestStation2) {
		this.nearestStation2 = nearestStation2;
	}


	public int getDistanceToStation2() {
		return distanceToStation2;
	}


	public void setDistanceToStation2(int distanceToStation2) {
		this.distanceToStation2 = distanceToStation2;
	}
	

}
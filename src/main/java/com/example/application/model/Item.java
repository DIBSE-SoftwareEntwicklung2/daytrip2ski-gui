package com.example.application.model;

import java.math.BigDecimal;
import java.util.Map;

public class Item {
	
	private int id;
	private String name;
	private String traffic;
	private String distance;
	private Map<String, Integer> slopes;
	private BigDecimal ticketPrice;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String image;
	
	public Item(int id, String name, String traffic, String distance, Map<String, Integer> slopes,
			BigDecimal ticketPrice, BigDecimal longitude, BigDecimal latitude, String image) {
		this.id = id;
		this.name = name;
		this.traffic = traffic;
		this.distance = distance;
		this.slopes = slopes;
		this.ticketPrice = ticketPrice;
		this.longitude = longitude;
		this.latitude = latitude;
		this.image = image;
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
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Map<String, Integer> getSlopes() {
		return slopes;
	}
	public void setSlopes(Map<String, Integer> slopes) {
		this.slopes = slopes;
	}
	public BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}

package com.example.application.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
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

    public Item(int id, String name, String traffic, String distance, Map<String, Integer> slopes, BigDecimal ticketPrice, BigDecimal longitude, BigDecimal latitude, String image) {
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
}

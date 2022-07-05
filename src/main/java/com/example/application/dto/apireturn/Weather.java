package com.example.application.dto.apireturn;

@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;
}

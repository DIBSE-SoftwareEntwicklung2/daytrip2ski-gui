package com.example.application.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherForecastReturn {
    public City city;
    public String cod;
    public double message;
    public int cnt;
    public ArrayList<List> list;
}

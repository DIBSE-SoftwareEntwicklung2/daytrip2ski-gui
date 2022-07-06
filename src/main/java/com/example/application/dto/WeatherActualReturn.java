package com.example.application.dto;

import com.example.application.dto.apireturn.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class WeatherActualReturn {
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}












package com.example.application.dto.apireturn;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class City {
    public int id;
    public String name;
    public Coord coord;
    public String country;
    public int population;
    public int timezone;
}

package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class City {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int population;
    private int timezone;
}

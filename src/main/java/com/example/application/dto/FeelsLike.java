package com.example.application.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeelsLike {
    public double day;
    public double night;
    public double eve;
    public double morn;
}

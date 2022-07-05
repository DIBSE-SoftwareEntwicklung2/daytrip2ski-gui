package com.example.application.dto.apireturn;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Temp {
    public double day;
    public double min;
    public double max;
    public double night;
    public double eve;
    public double morn;
}

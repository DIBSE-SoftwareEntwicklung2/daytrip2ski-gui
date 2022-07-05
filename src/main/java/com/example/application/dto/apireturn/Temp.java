package com.example.application.dto.apireturn;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Temp {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;
}

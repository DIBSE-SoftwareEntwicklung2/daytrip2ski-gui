package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Main{
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;
}

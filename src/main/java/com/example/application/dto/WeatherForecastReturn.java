package com.example.application.dto;

import com.example.application.dto.apireturn.City;
import com.example.application.dto.apireturn.List;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
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

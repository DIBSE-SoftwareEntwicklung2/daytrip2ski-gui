package com.example.application.dto;

import com.example.application.dto.apireturn.City;
import com.example.application.dto.apireturn.WeatherSummary;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherForecastReturn {
    private City city;
    private String cod;
    private double message;
    private int cnt;
    private List<WeatherSummary> weatherSummaries;
}

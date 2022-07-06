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
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class WeatherForecastReturn {
    public City city;
    public String cod;
    public double message;
    public int cnt;
    public List<WeatherSummary> list;
}

package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class WeatherSummary {
    private long dt;
    private int sunrise;
    private int sunset;
    private Temp temp;
    private FeelsLike feelsLike;
    private int pressure;
    private int humidity;
    private List<Weather> weather;
    private double speed;
    private int deg;
    private double gust;
    private int clouds;
    private double pop;
    private double rain;
}

package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class WeatherSummary {
    public long dt;
    public int sunrise;
    public int sunset;
    public Temp temp;
    public FeelsLike feels_like;
    public int pressure;
    public int humidity;
    public ArrayList<Weather> weather;
    public double speed;
    public int deg;
    public double gust;
    public int clouds;
    public double pop;
    public double rain;
}

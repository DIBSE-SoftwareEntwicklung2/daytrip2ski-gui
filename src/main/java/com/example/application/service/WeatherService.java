package com.example.application.service;

import com.example.application.dto.Skiresort;
import com.example.application.dto.WeatherActualReturn;
import com.example.application.dto.WeatherForecastReturn;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    public WeatherActualReturn getWeatherActual(Skiresort current) {
        //System.out.println( env.getProperty("api.baseurl"));
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( current.getWeatherActualUrl() );
        //"https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d"

        WeatherActualReturn weather = spec.retrieve().toEntity(WeatherActualReturn.class).block().getBody();
        return weather;
    }

    public WeatherForecastReturn getWeatherForecast(Skiresort current){
        System.out.println(current.getWeatherForecastUrl());
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(current.getWeatherForecastUrl());
        WeatherForecastReturn weather = spec.retrieve().toEntity(WeatherForecastReturn.class).block().getBody();
        return weather;
    }
}

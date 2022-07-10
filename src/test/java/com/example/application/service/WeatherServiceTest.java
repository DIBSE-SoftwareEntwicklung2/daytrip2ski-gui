package com.example.application.service;

import com.example.application.dto.Skiresort;
import com.example.application.dto.WeatherActualReturn;
import com.example.application.dto.WeatherForecastReturn;
import com.example.application.dto.apireturn.Weather;
import com.example.application.dto.apireturn.WeatherSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

/**
 * Test class for WeatherService.
 */
class WeatherServiceTest {
    WeatherService weatherService;
    Skiresort skiresort;

    /**
     * Preparing WeatherService.
     */
    @BeforeEach
    void setup() {
        weatherService = new WeatherService();
        skiresort = new Skiresort(1L, "KitzSki", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 14L, 17L, 9L, 1L, 8L, 50L, 70L, 50L, 30L, 150L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);
    }

    /**
     * Testing actual weather and mapping. If that works we know the http request works.
     */
    @Test
    void getWeatherActual() {
        Assertions.assertDoesNotThrow(() -> {
            WeatherActualReturn actualWeather = weatherService.getWeatherActual(skiresort);
            Weather weather = actualWeather.getWeather().get(0);
        });
    }

    /**
     * Testing weather forecast and mapping. If that works we know the http request works.
     */
    @Test()
    void getWeatherForecast() {
        Assertions.assertDoesNotThrow(() -> {
            WeatherForecastReturn forecastWeather = weatherService.getWeatherForecast(skiresort);

            for (int i = 0; i < 10; i++) {
                WeatherSummary weather = forecastWeather.list.get(i);
            }
        });
    }
}
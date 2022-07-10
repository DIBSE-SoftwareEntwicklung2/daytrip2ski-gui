package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.ResultDistanceMatrix;
import com.example.application.dto.Skiresort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

/**
 * Test class for GDistanceMatrixService.
 */
class GDistanceMatrixServiceTest {
    GDistanceMatrixService service;
    Person person;
    Skiresort skiresort;

    /**
     * Preparing GDistanceMatrixService.
     */
    @BeforeEach
    void setup() {
        service = new GDistanceMatrixService();
        person = new Person("Max", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 47.414747, 11.830936);
        skiresort = new Skiresort(1L, "KitzSki", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 14L, 17L, 9L, 1L, 8L, 50L, 70L, 50L, 30L, 150L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);
    }

    /**
     * Testing Google distance matrix with a known track and therefore a known distance.
     * If that works we know the http request works.
     */
    @Test
    void getDistanceMatrix() {
        ResultDistanceMatrix distance = service.getDistanceMatrix(person, skiresort);
        Assertions.assertTrue(distance.rows.get(0).elements.get(0).distance.value > 45000);
        Assertions.assertTrue(distance.rows.get(0).elements.get(0).distance.value < 55000);
    }
}
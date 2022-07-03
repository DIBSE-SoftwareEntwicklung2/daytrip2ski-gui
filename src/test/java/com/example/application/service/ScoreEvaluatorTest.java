package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Score;
import com.example.application.dto.Skiresort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static com.helger.commons.mock.CommonsAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.*;

class ScoreEvaluatorTest {
    static Person person;
    static Skiresort skiresort;
    static LocalDateTime localDateTime;
    Result result;
    @Mock
    RestPersonService restPersonService;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.of(2022, 11, 1, 10, 0, 0);
        MockitoAnnotations.openMocks(this);
        person = new Person("Max", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 7.8, 8.9);
        skiresort = new Skiresort(1L, "KitzSki", 47.444990D, 12.391430D, 800L, 2000L,
                2L, 3L, 1L, 22L, 27L, 9L, 1L, 8L, 68L,
                102L, 66L, 20L, 188L, "hard", 37L,
                "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html",
                "https://www.kitzski.at/",
                "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d",
                "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d",
                true, true, true, 59.5D, 44D, 29.5D,
                LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16),
                LocalTime.of(8, 30), LocalTime.of(16, 0),
                "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.",
                "TestRemark",
                "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.",
                true, 1);
        person.setId(1L);
        result = new Result();
    }

 //@Test
 //void evaluateScorePersonHasNoScore() {
 //    when(restPersonService.getScoreFromPerson(person)).thenReturn(null);
 //    result = ScoreEvaluator.evaluateScore(person, skiresort, localDateTime);
 //    assertFalse(result.valid);
 //    assertEquals("No score to compare found", result.valid_error);
 //}

    @Test
    void resolveTimeRecommended() {
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 10, 29, 10, 0, 0));
        assertTrue(result.recomendet);
        assertEquals(0, result.recomendet_errors.size());

        result = new Result();
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2023, 4, 16, 10, 0, 0));
        assertTrue(result.recomendet);
        assertEquals(0, result.recomendet_errors.size());

        result = new Result();
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 12, 15, 8, 30, 0));
        assertTrue(result.recomendet);
        assertEquals(0, result.recomendet_errors.size());

        result = new Result();
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 12, 15, 16, 0, 0));
        assertTrue(result.recomendet);
        assertEquals(0, result.recomendet_errors.size());
    }

    @Test
    void resolveTimeNotRecommendedActive() {
        skiresort.setIsActive(false);
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 12, 15, 10, 0, 0));
        assertFalse(result.recomendet);
        assertEquals(1, result.recomendet_errors.size());
        assertEquals("Resort is closed", result.recomendet_errors.get(0));
    }

    @Test
    void resolveTimeNotRecommendedDate() {
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 10, 28, 10, 0, 0));
        assertFalse(result.recomendet);
        assertEquals(1, result.recomendet_errors.size());
        assertEquals("Resort is out of Season", result.recomendet_errors.get(0));

        result = new Result();
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2023, 4, 17, 10, 0, 0));
        assertFalse(result.recomendet);
        assertEquals(1, result.recomendet_errors.size());
        assertEquals("Resort is out of Season", result.recomendet_errors.get(0));
    }

    @Test
    void resolveTimeNotRecommendedTime() {
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 12, 15, 8, 29, 0));
        assertFalse(result.recomendet);
        assertEquals(1, result.recomendet_errors.size());
        assertEquals("Resort is closed at this time", result.recomendet_errors.get(0));

        result = new Result();
        ScoreEvaluator.resolveTime(skiresort, result, LocalDateTime.of(2022, 12, 15, 16, 1, 0));
        assertFalse(result.recomendet);
        assertEquals(1, result.recomendet_errors.size());
        assertEquals("Resort is closed at this time", result.recomendet_errors.get(0));
    }

    @Test
    void evaluateScore() {
        Score score = new Score(1L, 0.5, 0.5, 0.5, 0.5, false, false, 100d, 20d, 30d);
        when(restPersonService.getScoreFromPerson(person)).thenReturn(score);
    }
}
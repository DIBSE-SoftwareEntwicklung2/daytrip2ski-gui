package com.example.application.service;

import com.example.application.dto.*;
import com.example.application.dto.gdistancematrix.*;
import com.example.application.dto.gdistancematrix.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

class ScoreEvaluatorTest {
    static Person person;
    static Skiresort skiresort;
    static Score score;
    static LocalDateTime localDateTime;
    static ResultDistanceMatrix distanceMatrix;
    static List<Skiresort> singleListSkiresorts;
    Result resultTest;
    @Mock
    private static RestPersonService rps;
    @Mock
    private static GDistanceMatrixService dms;
    @Mock
    private static RestSkiresortService rss;
    @InjectMocks
    private ScoreEvaluator scoreEvaluator;

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scoreEvaluator = new ScoreEvaluator(rps, dms, rss);
        localDateTime = LocalDateTime.of(2022, 11, 1, 10, 0, 0);

        person = new Person("Max", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 7.8, 8.9);
        skiresort = new Skiresort(1L, "KitzSki", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 22L, 27L, 9L, 1L, 8L, 68L, 102L, 66L, 20L, 188L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);
        person.setId(1L);
        resultTest = new Result();
        score = new Score(1L, 0.5d, 0.5d, 0.5d, 0.5d, true, true, 100d, 300d, 5d);

        distanceMatrix = new ResultDistanceMatrix();
        distanceMatrix.destination_addresses = new ArrayList<>();
        distanceMatrix.origin_addresses = new ArrayList<>();
        distanceMatrix.rows = new ArrayList<>();
        var element = new Element();
        element.status = "";
        var durationInTraffic = new DurationInTraffic();
        durationInTraffic.value = 3;
        durationInTraffic.text = "";
        var distance = new Distance();
        distance.value = 200;
        distance.text = "";
        var duration = new Duration();
        duration.value = 2;
        duration.text = "";
        element.duration_in_traffic = durationInTraffic;
        element.duration = duration;
        element.distance = distance;
        var row = new Row();
        var elementList = new ArrayList<Element>();
        elementList.add(element);
        row.elements = elementList;
        distanceMatrix.rows.add(row);
        distanceMatrix.status = "";

        var rssSkiresort = new Skiresort(1L, "KitzSki", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 22L, 27L, 9L, 1L, 8L, 68L, 102L, 66L, 20L, 188L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);

        singleListSkiresorts = new ArrayList<>();
        singleListSkiresorts.add(rssSkiresort);

        when(rss.getMinDistanceEasy()).thenReturn(singleListSkiresorts);
        when(rss.getMaxDistanceEasy()).thenReturn(singleListSkiresorts);
        when(rss.getMinDistanceIntermediate()).thenReturn(singleListSkiresorts);
        when(rss.getMaxDistanceIntermediate()).thenReturn(singleListSkiresorts);
        when(rss.getMinDistanceDifficult()).thenReturn(singleListSkiresorts);
        when(rss.getMaxDistanceDifficult()).thenReturn(singleListSkiresorts);
		
        row.elements = new ArrayList<>();
        row.elements.add(element);
        distanceMatrix.rows.add(row);
        distanceMatrix.status = "";
    }

    //@Test
    //void evaluateScorePersonHasNoScore() {
    //    when(restPersonService.getScoreFromPerson(person)).thenReturn(null);
    //    resultTest = ScoreEvaluator.evaluateScore(person, skiresort,  LocalDateTime.of(2022, 10, 29, 10, 0, 0));
    //    assertFalse(resultTest.valid);
    //    assertEquals("No score to compare found", resultTest.valid_error);
    //}

    @Test
    void resolveTimeRecommended() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 10, 29, 10, 0, 0));
        assertEquals(new Result(true, "", 36, true, new ArrayList<>()), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2023, 4, 16, 10, 0, 0));
        assertEquals(new Result(true, "", 36, true, new ArrayList<>()), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 8, 30, 0));
        assertEquals(new Result(true, "", 36, true, new ArrayList<>()), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 16, 0, 0));
        assertEquals(new Result(true, "", 36, true, new ArrayList<>()), result);
    }

    @Test
    void resolveTimeNotRecommendedActive() {
        skiresort.setIsActive(false);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);
        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));
        var expected = new ArrayList<String>();
        expected.add("Resort is closed");
        assertEquals(new Result(true, "", 36, false, expected), result);
    }

    @Test
    void resolveTimeNotRecommendedDate() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);
        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 10, 28, 10, 0, 0));
        var expected = new ArrayList<String>();
        expected.add("Resort is out of Season");
        assertEquals(new Result(true, "", 36, false, expected), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2023, 4, 17, 10, 0, 0));
        expected = new ArrayList<>();
        expected.add("Resort is out of Season");
        assertEquals(new Result(true, "", 36, false, expected), result);
    }

    @Test
    void resolveTimeNotRecommendedTime() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);
        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 8, 29, 0));
        var expected = new ArrayList<String>();
        expected.add("Resort is closed at this time");
        assertEquals(result, new Result(true, "", 36, false, expected));

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 16, 1, 0));
        expected = new ArrayList<>();
        expected.add("Resort is closed at this time");
        assertEquals(new Result(true, "", 36, false, expected), result);
    }

    @Test
    void resolveBooleansRecommended() {
        // All true.
        skiresort.setSkiRental(true);
        score.setRequiresRental(true);
        skiresort.setFamilyFriendly(true);
        score.setRequiresFamilyFriendly(true);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 36, true, expected), result);

        // All false.
        resultTest = new Result();
        skiresort.setSkiRental(false);
        score.setRequiresRental(false);
        skiresort.setFamilyFriendly(false);
        score.setRequiresFamilyFriendly(false);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 36, true, expected), result);

        // Not required Rental but there.
        resultTest = new Result();
        skiresort.setSkiRental(true);
        score.setRequiresRental(false);
        skiresort.setFamilyFriendly(true);
        score.setRequiresFamilyFriendly(true);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 36, true, expected), result);

        // Not required family friendly but there.
        resultTest = new Result();
        skiresort.setSkiRental(true);
        score.setRequiresRental(true);
        skiresort.setFamilyFriendly(true);
        score.setRequiresFamilyFriendly(false);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 36, true, expected), result);
    }

    @Test
    void resolveBooleansSkiRentalNotRecommended() {
        skiresort.setSkiRental(false);
        score.setRequiresRental(true);
        skiresort.setFamilyFriendly(true);
        score.setRequiresFamilyFriendly(true);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        expected.add("Does not have Rental");
        assertEquals(new Result(true, "", 36, false, expected), result);
    }

    @Test
    void resolveBooleansFamilyFriendlyNotRecommended() {
        skiresort.setSkiRental(true);
        score.setRequiresRental(true);
        skiresort.setFamilyFriendly(false);
        score.setRequiresFamilyFriendly(true);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        expected.add("Is not Family friendly");
        assertEquals(new Result(true, "", 36, false, expected), result);
    }
}
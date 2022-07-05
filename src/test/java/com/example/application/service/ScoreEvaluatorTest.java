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
    static List<Skiresort> singleListSkiresortsMin;
    static List<Skiresort> singleListSkiresortsMax;
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
        skiresort = new Skiresort(1L, "KitzSki", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 14L, 17L, 9L, 1L, 8L, 50L, 70L, 50L, 30L, 150L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);
        person.setId(1L);
        score = new Score(1L, 0.5d, 0.5d, 0.5d, 0.5d, true, true, 100d, 300d, 5d);

        distanceMatrix = new ResultDistanceMatrix();
        distanceMatrix.destination_addresses = new ArrayList<>();
        distanceMatrix.origin_addresses = new ArrayList<>();
        distanceMatrix.rows = new ArrayList<>();
        var element = new Element();
        element.status = "";
        var durationInTraffic = new DurationInTraffic();
        durationInTraffic.value = 10800;
        durationInTraffic.text = "";
        var distance = new Distance();
        distance.value = 200000;
        distance.text = "";
        var duration = new Duration();
        duration.value = 7200;
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

        var rssSkiresortMax = new Skiresort(2L, "Zell", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 30L, 37L, 9L, 1L, 8L, 80L, 100L, 80L, 60L, 240L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);
        var rssSkiresortMin = new Skiresort(3L, "Stubai", 47.444990D, 12.391430D, 800L, 2000L, 2L, 3L, 1L, 12L, 17L, 5L, 1L, 4L, 40L, 50L, 30L, 10L, 90L, "hard", 37L, "https://www.kitzski.at/de/bergurlaub-tirol/webcams.html", "https://www.kitzski.at/", "https://api.openweathermap.org/data/2.5/weather?lat=47.44499&lon=12.39143&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", "https://api.openweathermap.org/data/2.5/forecast/daily?lat=47.44499&lon=12.39143&cnt=10&units=metric&appid=27c73d44b5a87c8738cbe79bc5eca26d", true, true, true, 59.5D, 44D, 29.5D, LocalDate.of(2022, Month.OCTOBER, 29), LocalDate.of(2023, Month.APRIL, 16), LocalTime.of(8, 30), LocalTime.of(16, 0), "The openening times are provided by the ski resort KitzSki – Kitzbühel/\u200BKirchberg and can vary based on external conditions, day of the week, school holidays and public holidays.", "TestRemark", "The ski resort KitzSki – Kitzbühel/\u200BKirchberg is located in Kitzbühel (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbühel (District)) and in the Brixental (Austria, Tyrol (Tirol), Tiroler Unterland, Kitzbüheler Alpen). For skiing and snowboarding, there are 188 km of slopes and 45 km of ski routes available. 57 lifts transport the guests. The winter sports area is situated between the elevations of 800 and 2,000 m.", true, 1);

        singleListSkiresortsMin = new ArrayList<>();
        singleListSkiresortsMin.add(rssSkiresortMin);
        singleListSkiresortsMax = new ArrayList<>();
        singleListSkiresortsMax.add(rssSkiresortMax);

        when(rss.getMinDistanceEasy()).thenReturn(singleListSkiresortsMin);
        when(rss.getMaxDistanceEasy()).thenReturn(singleListSkiresortsMax);
        when(rss.getMinDistanceIntermediate()).thenReturn(singleListSkiresortsMin);
        when(rss.getMaxDistanceIntermediate()).thenReturn(singleListSkiresortsMax);
        when(rss.getMinDistanceDifficult()).thenReturn(singleListSkiresortsMin);
        when(rss.getMaxDistanceDifficult()).thenReturn(singleListSkiresortsMax);
        when(rss.getMinNumbersOfClimbingAids()).thenReturn(singleListSkiresortsMin.get(0).getTotalNumbersOfClimbingAids());
        when(rss.getMaxNumbersOfClimbingAids()).thenReturn(singleListSkiresortsMax.get(0).getTotalNumbersOfClimbingAids());
		
        row.elements = new ArrayList<>();
        row.elements.add(element);
        distanceMatrix.rows.add(row);
        distanceMatrix.status = "";
    }

    @Test
    void evaluateScorePersonHasNoScore() {
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(null);
        Result result = scoreEvaluator.evaluateScore(person, skiresort,  LocalDateTime.of(2022, 10, 29, 10, 0, 0));
        var expected = new ArrayList<String>();
        assertEquals(new Result(false, "No score to compare found", 0, true, expected), result);
    }

    @Test
    void resolveTimeRecommended() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 10, 29, 10, 0, 0));
        assertEquals(new Result(true, "", 70, true, new ArrayList<>()), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2023, 4, 16, 10, 0, 0));
        assertEquals(new Result(true, "", 70, true, new ArrayList<>()), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 8, 30, 0));
        assertEquals(new Result(true, "", 70, true, new ArrayList<>()), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 16, 0, 0));
        assertEquals(new Result(true, "", 70, true, new ArrayList<>()), result);
    }

    @Test
    void resolveTimeNotRecommendedActive() {
        skiresort.setIsActive(false);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);
        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));
        var expected = new ArrayList<String>();
        expected.add("Resort is closed");
        assertEquals(new Result(true, "", 70, false, expected), result);
    }

    @Test
    void resolveTimeNotRecommendedDate() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);
        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 10, 28, 10, 0, 0));
        var expected = new ArrayList<String>();
        expected.add("Resort is out of Season");
        assertEquals(new Result(true, "", 70, false, expected), result);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2023, 4, 17, 10, 0, 0));
        expected = new ArrayList<>();
        expected.add("Resort is out of Season");
        assertEquals(new Result(true, "", 70, false, expected), result);
    }

    @Test
    void resolveTimeNotRecommendedTime() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);
        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 8, 29, 0));
        var expected = new ArrayList<String>();
        expected.add("Resort is closed at this time");
        assertEquals(result, new Result(true, "", 70, false, expected));

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 16, 1, 0));
        expected = new ArrayList<>();
        expected.add("Resort is closed at this time");
        assertEquals(new Result(true, "", 70, false, expected), result);
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
        assertEquals(new Result(true, "", 70, true, expected), result);

        // All false.
        skiresort.setSkiRental(false);
        score.setRequiresRental(false);
        skiresort.setFamilyFriendly(false);
        score.setRequiresFamilyFriendly(false);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 70, true, expected), result);

        // Not required Rental but there.
        skiresort.setSkiRental(true);
        score.setRequiresRental(false);
        skiresort.setFamilyFriendly(true);
        score.setRequiresFamilyFriendly(true);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 70, true, expected), result);

        // Not required family friendly but there.
        skiresort.setSkiRental(true);
        score.setRequiresRental(true);
        skiresort.setFamilyFriendly(true);
        score.setRequiresFamilyFriendly(false);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 70, true, expected), result);
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
        assertEquals(new Result(true, "", 70, false, expected), result);
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
        assertEquals(new Result(true, "", 70, false, expected), result);
    }

    @Test
    void resolveBudgetRecommended() {
        score.setBudged(50D);
        skiresort.setPriceDayTicketAdults(49D);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));
        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 70, true, expected), result);

        score.setBudged(50D);
        skiresort.setPriceDayTicketAdults(50D);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));
        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 70, true, expected), result);
    }

    @Test
    void resolveBudgetNotRecommended() {
        score.setBudged(50D);
        skiresort.setPriceDayTicketAdults(51D);
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));
        var expected = new ArrayList<String>();
        expected.add("Does not fit Budget");
        assertEquals(new Result(true, "", 70, false, expected), result);
    }

    // Real calculation of percentage value.
    // Min / Max of all skiresorts.
    // Easy: (easy - min) / (max - min) -> (1 - Abs(EasyAffinity - EasyCalculated)) * 10 (min = 50, max = 100, default = 70, defaultAffinity = 0.5, defaultResult = 9)
    // Intermediate: (easy - min) / (max - min) -> (1 - Abs(IntermediateAffinity - IntermediateCalculated)) * 10 (min = 30, max = 80, default = 50, defaultAffinity = 0.5, defaultResult = 9)
    // Difficult: (easy - min) / (max - min) -> (1 - Abs(DifficultAffinity - DifficultCalculated)) * 10 (min = 10, max = 60, default = 30, defaultAffinity = 0.5, defaultResult = 9)
    // Variety (TotalClimbingAids): (variety - min) / (max - min) -> (1 - Abs(VarietyAffinity - VarietyCalculated)) * 10 (min = 40, max = 80, default = 50, defaultAffinity = 0.5, defaultResult = 7.5 = 8)

    // Min / Max of person
    // Distance: recommended?; (distance - max) / (1000m - max) -> (1 - DistanceCalculated) * 10 (distance = 200, max = 300, defaultResult = 3.344 = 3)
    // Driving Time: recommended?; (durationInTraffic - max) / (10min - max) -> (1 - TimeCalculated) * 10 (durationInTraffic = 3, max = 5, defaultResult = 4.138 = 4)
    // DefaultResult = 42 / 6 * 10 = 70

    @Test
    void resolveDefault() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 70, true, expected), result);
    }

    @Test
    void resolveSlopeEasy() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        // High affinity to easy slopes and many easy slopes. -> EasyResult = 10
        score.setAffinityToEasyTracks(1D);
        skiresort.setDistanceEasy(99L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 72, true, expected), result);

        // Low affinity to easy slopes and less easy slopes. -> EasyResult = 10
        score.setAffinityToEasyTracks(0D);
        skiresort.setDistanceEasy(51L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 72, true, expected), result);

        // Low affinity to easy slopes and many easy slopes. -> EasyResult = 0
        score.setAffinityToEasyTracks(0D);
        skiresort.setDistanceEasy(99L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 55, true, expected), result);

        // High affinity to easy slopes and less easy slopes. -> EasyResult = 0
        score.setAffinityToEasyTracks(1D);
        skiresort.setDistanceEasy(51L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 55, true, expected), result);

        // Middle high affinity to easy slopes and normal easy slopes. -> EasyResult = 7
        score.setAffinityToEasyTracks(0.7D);
        skiresort.setDistanceEasy(70L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 67, true, expected), result);

        // Middle low affinity to easy slopes and normal easy slopes. -> EasyResult = 5
        score.setAffinityToEasyTracks(0.3D);
        skiresort.setDistanceEasy(90L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 63, true, expected), result);
    }

    @Test
    void resolveSlopeIntermediate() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        // High affinity to intermediate slopes and many intermediate slopes. -> IntermediateResult = 10
        score.setAffinityToIntermediateTracks(1D);
        skiresort.setDistanceIntermediate(79L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 72, true, expected), result);

        // Low affinity to intermediate slopes and less intermediate slopes. -> IntermediateResult = 10
        score.setAffinityToIntermediateTracks(0D);
        skiresort.setDistanceIntermediate(31L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 72, true, expected), result);

        // Low affinity to intermediate slopes and many intermediate slopes. -> IntermediateResult = 0
        score.setAffinityToIntermediateTracks(0D);
        skiresort.setDistanceIntermediate(79L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 55, true, expected), result);

        // High affinity to intermediate slopes and less intermediate slopes. -> IntermediateResult = 0
        score.setAffinityToIntermediateTracks(1D);
        skiresort.setDistanceIntermediate(31L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 55, true, expected), result);

        // Middle high affinity to intermediate slopes and normal intermediate slopes. -> IntermediateResult = 5
        score.setAffinityToIntermediateTracks(0.7D);
        skiresort.setDistanceIntermediate(40L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 63, true, expected), result);

        // Middle low affinity to intermediate slopes and normal intermediate slopes. -> IntermediateResult = 4
        score.setAffinityToIntermediateTracks(0.3D);
        skiresort.setDistanceIntermediate(75L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 62, true, expected), result);
    }

    @Test
    void resolveSlopeDifficult() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        // High affinity to difficult slopes and many difficult slopes. -> DifficultResult = 10
        score.setAffinityToDifficultTracks(1D);
        skiresort.setDistanceDifficult(59L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 72, true, expected), result);

        // Low affinity to difficult slopes and less difficult slopes. -> DifficultResult = 10
        score.setAffinityToDifficultTracks(0D);
        skiresort.setDistanceDifficult(11L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 72, true, expected), result);

        // Low affinity to difficult slopes and many difficult slopes. -> DifficultResult = 0
        score.setAffinityToDifficultTracks(0D);
        skiresort.setDistanceDifficult(59L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 55, true, expected), result);

        // High affinity to difficult slopes and less difficult slopes. -> DifficultResult = 0
        score.setAffinityToDifficultTracks(1D);
        skiresort.setDistanceDifficult(11L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 55, true, expected), result);

        // Middle high affinity to difficult slopes and normal difficult slopes. -> DifficultResult = 5
        score.setAffinityToDifficultTracks(0.7D);
        skiresort.setDistanceDifficult(20L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 63, true, expected), result);

        // Middle low affinity to difficult slopes and normal difficult slopes. -> DifficultResult = 4
        score.setAffinityToDifficultTracks(0.3D);
        skiresort.setDistanceDifficult(55L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 62, true, expected), result);
    }

    @Test
    void resolveVariety() {
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        // High affinity to difficult slopes and many difficult slopes. -> DifficultResult = 10
        score.setVariety(1D);
        skiresort.setTotalNumbersOfClimbingAids(79L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        assertEquals(new Result(true, "", 73, true, expected), result);

        // Low affinity to difficult slopes and less difficult slopes. -> DifficultResult = 10
        score.setVariety(0D);
        skiresort.setTotalNumbersOfClimbingAids(41L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 73, true, expected), result);

        // Low affinity to difficult slopes and many difficult slopes. -> DifficultResult = 0
        score.setVariety(0D);
        skiresort.setTotalNumbersOfClimbingAids(79L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 57, true, expected), result);

        // High affinity to difficult slopes and less difficult slopes. -> DifficultResult = 0
        score.setVariety(1D);
        skiresort.setTotalNumbersOfClimbingAids(41L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 57, true, expected), result);

        // Middle high affinity to difficult slopes and normal difficult slopes. -> DifficultResult = 4
        score.setVariety(0.7D);
        skiresort.setTotalNumbersOfClimbingAids(45L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 63, true, expected), result);

        // Middle low affinity to difficult slopes and normal difficult slopes. -> DifficultResult = 7
        score.setVariety(0.3D);
        skiresort.setTotalNumbersOfClimbingAids(65L);
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 68, true, expected), result);
    }

    @Test
    void resolveDistance() {
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        // Too long Distance. -> DistanceResult = 0 + not recommended
        distanceMatrix.rows.get(0).elements.get(0).distance.value = 301000;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        expected.add("Destination is to far away");
        assertEquals(new Result(true, "", 65, false, expected), result);

        // Long Distance. -> DistanceResult = 0
        distanceMatrix.rows.get(0).elements.get(0).distance.value = 300000;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 65, true, expected), result);

        // Short Distance. -> DistanceResult = 10
        distanceMatrix.rows.get(0).elements.get(0).distance.value = 5000;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 82, true, expected), result);

        // Middle short Distance. -> DistanceResult = 8
        distanceMatrix.rows.get(0).elements.get(0).distance.value = 50000;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 78, true, expected), result);

        // Middle long Distance. -> DistanceResult = 2
        distanceMatrix.rows.get(0).elements.get(0).distance.value = 250000;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 68, true, expected), result);
    }

    @Test
    void resolveTime() {
        when(rps.getScoreFromPerson(any(Long.class))).thenReturn(score);

        // Too long duration. -> TimeResult = 0 + not recommended
        distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value = 18060;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        Result result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        var expected = new ArrayList<String>();
        expected.add("Destination takes to long to drive to");
        assertEquals(new Result(true, "", 63, false, expected), result);

        // Long duration. -> TimeResult = 0
        distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value = 18000;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 63, true, expected), result);

        // Short duration. -> TimeResult = 10
        distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value = 1200;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 80, true, expected), result);

        // Middle short duration. -> TimeResult = 8
        distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value = 3600;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 77, true, expected), result);

        // Middle long duration. -> TimeResult = 2
        distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value = 14400;
        when(dms.getDistanceMatrix(any(Person.class), any(Skiresort.class))).thenReturn(distanceMatrix);

        result = scoreEvaluator.evaluateScore(person, skiresort, LocalDateTime.of(2022, 12, 15, 10, 0, 0));

        expected = new ArrayList<>();
        assertEquals(new Result(true, "", 67, true, expected), result);
    }
}
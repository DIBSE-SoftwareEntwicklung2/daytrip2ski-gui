package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Score;
import com.example.application.dto.Skiresort;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Math.abs;


/**
 * This Class is used to Evaluate how much a Skiresort fits to a Person
 */
public class ScoreEvaluator {
    private final RestPersonService rps;
    private final GDistanceMatrixService dms;
    private final RestSkiresortService rss;

    /**
     * Constructor
     *
     * @param rps RestPersonService
     * @param dms GDistanceMatrixService
     * @param rss RestSkiresortService
     */
    public ScoreEvaluator(@NotNull RestPersonService rps, @NotNull GDistanceMatrixService dms, @NotNull RestSkiresortService rss) {
        this.rps = rps;
        this.dms = dms;
        this.rss = rss;
    }

    /**
     * Evaluates a Person and a Skiresort and returnes (if Valid) a Score and/or multiple reasons why it is not recommendet
     *
     * @param person          Person
     * @param skiresort       Skiresort
     * @param dateTimeForTrip TimeoftheTrip
     * @return Result object
     */
    public Result evaluateScore(@NotNull Person person, @NotNull Skiresort skiresort, @NotNull LocalDateTime dateTimeForTrip) {
        Result result = new Result();
        Score score = rps.getScoreFromPerson(person.getId());
        if (score == null) {
            result.setValid(false);
            result.setValidError("No score to compare found");
            return result;
        }

        result.setValid(true);
        resolveTime(skiresort, result, dateTimeForTrip);
        resolveBooleans(skiresort, result, score);
        resolveBudged(skiresort, result, score);
        resolveSlopeDistance(skiresort, result, score);
        resolveVariety(skiresort, result, score);
        resolveDistance(person, skiresort, result, score);
        result.setScore((int) Math.round((result.getScore() * (10.0 / 6))));
        return result;
    }

    private void resolveTime(Skiresort skiresort, Result result, LocalDateTime dateTimeForTrip) {
        /*
         Assuming that we calculate that the user can teleport instantly to the destination at the moment
         we can add a preferred visiting time later on
        */
        if (Boolean.FALSE.equals(skiresort.getIsActive())) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Resort is closed");
        }
        if (dateTimeForTrip.toLocalDate().isBefore(skiresort.getSeasonFrom()) || dateTimeForTrip.toLocalDate().isAfter(skiresort.getSeasonTo())) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Resort is out of Season");
        }
        if (dateTimeForTrip.toLocalTime().isBefore(skiresort.getOpeningHoursFrom()) || dateTimeForTrip.toLocalTime().isAfter(skiresort.getOpeningHoursTo())) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Resort is closed at this time");
        }
    }

    private void resolveBooleans(Skiresort skiresort, Result result, Score score) {
        if (Boolean.TRUE.equals(score.getRequiresRental()) && Boolean.FALSE.equals(skiresort.getSkiRental())) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Does not have Rental");
        }
        if (Boolean.TRUE.equals(score.getRequiresFamilyFriendly()) && Boolean.FALSE.equals(skiresort.getFamilyFriendly())) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Is not Family friendly");
        }
    }

    private void resolveBudged(Skiresort skiresort, Result result, Score score) {
        // I am also assuming right now that only one adult is going for one day
        if (score.getBudged() < skiresort.getPriceDayTicketAdults()) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Does not fit Budget");
        }
    }

    private void resolveSlopeDistance(Skiresort skiresort, Result result, Score score) {
        Long easyMin = rss.getMinDistanceEasy().get(0).getDistanceEasy();
        Long easyMax = rss.getMaxDistanceEasy().get(0).getDistanceEasy();
        Long intermediateMin = rss.getMinDistanceIntermediate().get(0).getDistanceIntermediate();
        Long intermediateMax = rss.getMaxDistanceIntermediate().get(0).getDistanceIntermediate();
        Long difficultMin = rss.getMinDistanceDifficult().get(0).getDistanceDifficult();
        Long difficultMax = rss.getMaxDistanceDifficult().get(0).getDistanceDifficult();

        //scale results to a value between 0 and 1
        Double scaledEasy;
        if (!Objects.equals(easyMin, easyMax)) {
            scaledEasy = (skiresort.getDistanceEasy().doubleValue() - easyMin.doubleValue()) / (easyMax.doubleValue() - easyMin.doubleValue());
        } else {
            scaledEasy = 1d;
        }

        Double scaledIntermediate;
        if (!Objects.equals(intermediateMin, intermediateMax)) {
            scaledIntermediate = (skiresort.getDistanceIntermediate().doubleValue() - intermediateMin.doubleValue()) / (intermediateMax.doubleValue() - intermediateMin.doubleValue());
        } else {
            scaledIntermediate = 1d;
        }

        Double scaledDifficult;
        if (!Objects.equals(difficultMin, difficultMax)) {
            scaledDifficult = (skiresort.getDistanceDifficult().doubleValue() - difficultMin.doubleValue()) / (difficultMax.doubleValue() - difficultMin.doubleValue());
        } else {
            scaledDifficult = 1d;
        }

        //evaluate how close the values are and transform them to a value between 0 and 10
        double easyResult = abs(score.getAffinityToEasyTracks() - scaledEasy);
        easyResult = (1 - easyResult) * 10;
        double intermediateResult = abs(score.getAffinityToIntermediateTracks() - scaledIntermediate);
        intermediateResult = (1 - intermediateResult) * 10;
        double difficultResult = abs(score.getAffinityToDifficultTracks() - scaledDifficult);
        difficultResult = (1 - difficultResult) * 10;

        result.setScore(result.getScore() + (int) Math.round(easyResult));
        result.setScore(result.getScore() + (int) Math.round(intermediateResult));
        result.setScore(result.getScore() + (int) Math.round(difficultResult));
    }

    private void resolveVariety(Skiresort skiresort, Result result, Score score) {
        double min = rss.getMinNumbersOfClimbingAids().doubleValue();
        double max = rss.getMaxNumbersOfClimbingAids().doubleValue();
        double current = skiresort.getTotalNumbersOfClimbingAids();

        double varietyResult = (current - min) / (max - min);
        varietyResult = abs(score.getVariety() - varietyResult);
        varietyResult = (1 - varietyResult) * 10;
        result.setScore(result.getScore() + (int) Math.round(varietyResult));
    }

    private void resolveDistance(Person person, Skiresort skiresort, Result result, Score score) {
        var distanceMatrix = dms.getDistanceMatrix(person, skiresort);

        if (distanceMatrix.rows.get(0).elements.get(0).distance == null || distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic == null) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("No information on driving distance and driving time");
            return;
        }

        double maxDistance = score.getMaxDistance() * 1000; //convert to meters
        double distance = distanceMatrix.rows.get(0).elements.get(0).distance.value;
        double distanceResult = distance / maxDistance;
        if (distanceResult > 1) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Destination is to far away");
        } else {
            double minDistance = 1000; //1km benchmark for minimum
            distanceResult = ((distance - maxDistance) / (minDistance - maxDistance)) * 10;
            result.setScore(result.getScore() + (int) Math.round(distanceResult));
        }

        double maxTime = score.getMaxDrivingTime() * 60 * 60; //convert to seconds
        double time = distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value;
        double timeResult = time / maxTime;
        if (timeResult > 1) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Destination takes to long to drive to");
        } else {
            // 10 minutes benchmark for minimum
            double minTime = 10d * 60d;
            timeResult = ((time - maxTime) / (minTime - maxTime)) * 10;
            result.setScore(result.getScore() + (int) Math.round(timeResult));
        }
    }
}

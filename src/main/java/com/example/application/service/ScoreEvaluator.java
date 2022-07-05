package com.example.application.service;

import com.example.application.dto.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Math.abs;

public class ScoreEvaluator {
    private final RestPersonService rps;
    private final GDistanceMatrixService dms;

    public ScoreEvaluator(RestPersonService rps, GDistanceMatrixService dms) {
        this.rps = rps;
        this.dms = dms;
    }

    public Result evaluateScore(Person person, Skiresort skiresort, LocalDateTime dateTimeForTrip) {
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
        result.setScore((int) (result.getScore() * (10.0 / 6)));
        return result;
    }

    private void resolveTime(Skiresort skiresort, Result result, LocalDateTime dateTimeForTrip) {
        /*
         Assuming that we calculate that the user can teleport instantly to the destination at the moment
         we can add a preferred visiting time later on
        */
        if (!skiresort.getIsActive()) {
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
        if (score.getRequiresRental() && !skiresort.getSkiRental()) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Does not have Rental");
        }
        if (score.getRequiresFamilyFriendly() && !skiresort.getFamilyFriendly()) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Is not Family friendly");
        }
    }

    private void resolveBudged(Skiresort skiresort, Result result, Score score) {
        /*
        i am also assuming right now that only one adult is going for one day
         */
        if (score.getBudged() < skiresort.getPriceDayTicketAdults()) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Does not fit Budget");
        }
    }

    private void resolveSlopeDistance(Skiresort skiresort, Result result, Score score) {
        RestSkiresortService getStats = new RestSkiresortService();
        Long easyMin = getStats.getMinDistanceEasy().get(0).getDistanceEasy();
        Long easyMax = getStats.getMaxDistanceEasy().get(0).getDistanceEasy();
        Long intermediateMin = getStats.getMinDistanceIntermediate().get(0).getDistanceIntermediate();
        Long intermediateMax = getStats.getMaxDistanceIntermediate().get(0).getDistanceIntermediate();
        Long difficultMin = getStats.getMinDistanceDifficult().get(0).getDistanceDifficult();
        Long difficultMax = getStats.getMaxDistanceDifficult().get(0).getDistanceDifficult();

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

        result.setScore(result.getScore() + (int) easyResult);
        result.setScore(result.getScore() + (int) intermediateResult);
        result.setScore(result.getScore() + (int) difficultResult);
    }

    private void resolveVariety(Skiresort skiresort, Result result, Score score) {
        RestSkiresortService getStats = new RestSkiresortService();

        double min = getStats.getMinNumbersOfClimbingAids().doubleValue();
        double max = getStats.getMaxNumbersOfClimbingAids().doubleValue();
        double current = skiresort.getTotalNumbersOfClimbingAids();

        double varietyResult = (current - min) / (max - min);
        varietyResult = abs(score.getVariety() - varietyResult);
        varietyResult = (1 - varietyResult) * 10;
        result.setScore(result.getScore() + (int) varietyResult);
    }

    private void resolveDistance(Person person, Skiresort skiresort, Result result, Score score) {
        var distanceMatrix = dms.getDistanceMatrix(person, skiresort);

        if (distanceMatrix.getRows().get(0).getElements().get(0).getDistance() == null || distanceMatrix.getRows().get(0).getElements().get(0).getDurationInTraffic() == null) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("No information on driving distance and driving time");
            return;
        }

        double maxDistance = score.getMaxDistance() * 1000; //convert to meters
        double distance = distanceMatrix.getRows().get(0).getElements().get(0).getDistance().getValue();
        double distanceResult = distance / maxDistance;
        if (distanceResult > 1) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Destination is to far away");
        } else {
            double minDistance = 1000; //1km benchmark for minimum
            distanceResult = (1 - ((distance - maxDistance) / (minDistance - maxDistance))) * 10;
            result.setScore(result.getScore() + (int) distanceResult);
        }

        double maxTime = score.getMaxDrivingTime() * 60 * 60; //convert to seconds
        double time = distanceMatrix.getRows().get(0).getElements().get(0).getDurationInTraffic().getValue();
        double timeResult = time / maxTime;
        if (timeResult > 1) {
            result.setRecommended(false);
            result.getRecommendedErrors().add("Destination takes to long to drive to");
        } else {
            // 10 minutes benchmark for minimum
            double minTime = 10d * 60d;
            timeResult = (1 - ((time - maxTime) / (minTime - maxTime))) * 10;
            result.setScore(result.getScore() + (int) timeResult);
        }
    }
}

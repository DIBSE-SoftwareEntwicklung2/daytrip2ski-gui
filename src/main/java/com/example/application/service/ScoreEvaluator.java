package com.example.application.service;

import com.example.application.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static com.example.application.service.GDistanceMatrixService.getDistanceMatrix;
import static java.lang.Math.abs;

public class ScoreEvaluator {
    private ScoreEvaluator() {
        throw new IllegalStateException(ScoreEvaluator.class.getName());
    }

    public static Result evaluateScore(Person person, Skiresort skiresort) {
        Result result = new Result();
        RestPersonService rps = new RestPersonService();
        Score score = rps.getScoreFromPerson(person);
        if (score == null) {
            result.valid = false;
            result.valid_error = "No score to compare found";
            return result;
        }

        result.valid = true;
        resolveTime(skiresort, result);
        resolveBooleans(skiresort, result, score);
        resolveBudged(skiresort, result, score);
        resolveSlopeDistance(skiresort, result, score);
        resolveVariety(skiresort, result, score);
        resolveDistance(person, skiresort, result, score);

        result.score *= (10/6);

        return result;
    }

    private static void resolveTime(Skiresort skiresort, Result result) {
        /*
         Assuming that we calculate that the user can teleport instantly to the destination at the moment
         we can add a preferred visiting time later on
        */
        if (!skiresort.getIsActive()) {
            result.recomendet = false;
            result.recomendet_errors.add("Resort is closed");
        }
        if (LocalDate.now().isBefore(skiresort.getSeasonFrom()) || LocalDate.now().isAfter(skiresort.getSeasonTo())) {
            result.recomendet = false;
            result.recomendet_errors.add("Resort is out of Season");
        }
        if (LocalTime.now().isBefore(skiresort.getOpeningHoursFrom()) || LocalTime.now().isAfter(skiresort.getOpeningHoursTo())) {
            result.recomendet = false;
            result.recomendet_errors.add("Resort is closed at this time");
        }
    }

    private static void resolveBooleans(Skiresort skiresort, Result result, Score score) {
        if (score.getRequiresRental() && !skiresort.getSkiRental()) {
            result.recomendet = false;
            result.recomendet_errors.add("Does not have Rental");
        }
        if (score.getRequiresFamilyFriendly() && !skiresort.getFamilyFriendly()) {
            result.recomendet = false;
            result.recomendet_errors.add("Is not Family friendly");
        }
    }

    private static void resolveBudged(Skiresort skiresort, Result result, Score score) {
        /*
        i am also assuming right now that only one adult is going for one day
         */
        if (score.getBudged() < skiresort.getPriceDayTicketAdults()) {
            result.recomendet = false;
            result.recomendet_errors.add("Does not fit Budget");
        }
    }

    private static void resolveSlopeDistance(Skiresort skiresort, Result result, Score score) {
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

        result.score += (int) easyResult;
        result.score += (int) intermediateResult;
        result.score += (int) difficultResult;
    }

    private static void resolveVariety(Skiresort skiresort, Result result, Score score) {
        RestSkiresortService getStats = new RestSkiresortService();

        double min = getStats.getMinNumbersOfClimbingAids().doubleValue();
        double max = getStats.getMaxNumbersOfClimbingAids().doubleValue();
        double current = skiresort.getTotalNumbersOfClimbingAids();

        double varietyResult = (current - min) / (max - min);
        varietyResult = abs(score.getVariety() - varietyResult);
        varietyResult = (1 - varietyResult) * 10;
        result.score += (int) varietyResult;
    }

    private static void resolveDistance(Person person, Skiresort skiresort, Result result, Score score) {
        ResultDistanceMatrix distanceMatrix = getDistanceMatrix(person, skiresort);

        if (distanceMatrix.rows.get(0).elements.get(0).distance == null || distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic == null) {
            result.recomendet = false;
            result.recomendet_errors.add("No information on driving distance and driving time");
            return;
        }

        double maxDistance = score.getMaxDistance() * 1000; //convert to meters
        double distance = distanceMatrix.rows.get(0).elements.get(0).distance.value;
        double distanceResult = distance / maxDistance;
        if (distanceResult > 1) {
            result.recomendet = false;
            result.recomendet_errors.add("Destination is to far away");
        } else {
            double minDistance = 1000; //1km benchmark for minimum
            distanceResult = (1 - ((distance - maxDistance) / (minDistance - maxDistance))) * 10;
            result.score += (int) distanceResult;
        }

        double maxTime = score.getMaxDrivingTime() * 60 * 60; //convert to seconds
        double time = distanceMatrix.rows.get(0).elements.get(0).duration_in_traffic.value;
        double timeResult = time / maxTime;
        if (timeResult > 1) {
            result.recomendet = false;
            result.recomendet_errors.add("Destination takes to long to drive to");
        } else {
            // 10 minutes benchmark for minimum
            double minTime = 10d * 60d;
            timeResult = (1 - ((time - maxTime) / (minTime - maxTime))) * 10;
            result.score += (int) timeResult;
        }

    }
}

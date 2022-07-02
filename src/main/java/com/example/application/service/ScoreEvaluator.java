package com.example.application.service;

import com.example.application.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static com.example.application.service.GDistanceMatrixService.getDistanceMatrix;
import static java.lang.Math.abs;

public class ScoreEvaluator {


    static public Result EvaluateScore(Person person, Skiresort skiresort){
        Result result = new Result();
        RestPersonService rps = new RestPersonService();

        Score score = rps.getScorefromPerson(person);

        if(score == null){
            result.valid = false;
            result.valid_error = "No score to compare found";
            return result;
        }
        result.valid = true;

        resolveTime(skiresort,result);

        resolveBooleans(skiresort, result, score);

        resolveBudged(skiresort, result, score);

        resolveSlopeDistance(skiresort, result, score);

        resolveVariety(skiresort, result, score);

        resolveDistance(person, skiresort, result, score);

        return result;
    }

    static private void resolveTime(Skiresort skiresort, Result result){
        /*
         i am assuming that we calculate that the user can teleport instantly to the destination at the moment
         we can add a prefered visiting time later on
        */
        if(!skiresort.getIsActive()){
            result.recomendet = false;
            result.recomendet_errors.add("Resort is closed");
        }
        if(LocalDate.now().isBefore(skiresort.getSeasonFrom()) || LocalDate.now().isAfter(skiresort.getSeasonTo())){
            result.recomendet = false;
            result.recomendet_errors.add("Resort is out of Season");
        }
        if(LocalTime.now().isBefore(skiresort.getOpeningHoursFrom()) || LocalTime.now().isAfter(skiresort.getOpeningHoursTo())){
            result.recomendet = false;
            result.recomendet_errors.add("Resort is closed at this time");
        }
    }

    static private void resolveBooleans(Skiresort skiresort, Result result, Score score){
        if(score.getRequiresRental() && !skiresort.getSkiRental()){
            result.recomendet = false;
            result.recomendet_errors.add("Does not have Rental");
        }
        if(score.getRequiresFamilyFriendly() && !skiresort.getFamilyFriendly()){
            result.recomendet = false;
            result.recomendet_errors.add("Is not Family friendly");
        }
    }

    static private void resolveBudged(Skiresort skiresort, Result result, Score score){
        /*
        i am also assuming right now that only one adult is going for one day
         */
        if(score.getBudged() < skiresort.getPriceDayTicketAdults()){
            result.recomendet = false;
            result.recomendet_errors.add("Does not fit Budget");
        }
    }

    static private void resolveSlopeDistance(Skiresort skiresort, Result result, Score score){
        RestSkiresortService getStats = new RestSkiresortService();

        Long easy_min = getStats.getmindistanceeasy().get(0).getDistanceEasy();
        Long easy_max = getStats.getmaxdistanceeasy().get(0).getDistanceEasy();

        Long intermediate_min = getStats.getmindistanceintermediate().get(0).getDistanceIntermediate();
        Long intermediate_max = getStats.getmaxdistanceintermediate().get(0).getDistanceIntermediate();

        Long difficult_min = getStats.getmindistancedifficult().get(0).getDistanceDifficult();
        Long difficult_max = getStats.getmaxdistancedifficult().get(0).getDistanceDifficult();


        //scale results to a value between 0 and 1
        Double scaled_easy;
        if(!Objects.equals(easy_min, easy_max)){
           scaled_easy = (skiresort.getDistanceEasy().doubleValue() - easy_min.doubleValue()) / (easy_max.doubleValue() - easy_min.doubleValue());
        }else{
           scaled_easy = 1d;
        }
        Double scaled_intermediate;
        if(!Objects.equals(intermediate_min, intermediate_max)){
           scaled_intermediate = (skiresort.getDistanceIntermediate().doubleValue() - intermediate_min.doubleValue()) / (intermediate_max.doubleValue() - intermediate_min.doubleValue());
        }else{
           scaled_intermediate = 1d;
        }
        Double scaled_difficult;
        if(!Objects.equals(difficult_min, difficult_max)){
            scaled_difficult = (skiresort.getDistanceDifficult().doubleValue() - difficult_min.doubleValue()) / (difficult_max.doubleValue() - difficult_min.doubleValue());
        }else{
            scaled_difficult = 1d;
        }

        //evaluate how close the values are and transform them to a value between 0 and 10
        double easy_result = abs(score.getAffinityToEasyTracks() - scaled_easy);
        easy_result = (1 - easy_result) * 10;
        double intermediate_result = abs(score.getAffinityToIntermediateTracks() - scaled_intermediate);
        intermediate_result = (1 - intermediate_result) * 10;
        double difficult_result = abs(score.getAffinityToDifficultTracks() - scaled_difficult);
        difficult_result = (1 - difficult_result) * 10;

        result.score += (int)easy_result;
        result.score += (int)intermediate_result;
        result.score += (int)difficult_result;
    }

    static private void resolveVariety(Skiresort skiresort, Result result, Score score){
        RestSkiresortService getStats = new RestSkiresortService();

        double min = getStats.getminnumbersofclimbingaids().doubleValue();
        double max = getStats.getmaxnumbersofclimbingaids().doubleValue();
        double current = skiresort.getTotalNumbersOfClimbingAids();

        double variety_result = (current - min) / (max - min);
        variety_result = abs(score.getVariety() - variety_result);
        variety_result =  (1 - variety_result) * 10;
        result.score += (int)variety_result;
    }

    static private void resolveDistance(Person person, Skiresort skiresort, Result result, Score score){
        ResultDistanceMatrix distancematrix = getDistanceMatrix(person, skiresort);

        if(distancematrix.rows.get(0).elements.get(0).distance == null || distancematrix.rows.get(0).elements.get(0).duration_in_traffic == null){
            result.recomendet = false;
            result.recomendet_errors.add("No information on driving distance and driving time");
            return;
        }

        double max_distance = score.getMaxDistance() * 1000; //convert to meters
        double distance = distancematrix.rows.get(0).elements.get(0).distance.value;
        double distance_result = distance / max_distance;
        if(distance_result > 1){
            result.recomendet = false;
            result.recomendet_errors.add("Destination is to far away");
        }else{
           double min_distance = 1000; //1km benchmark for minimum
           distance_result = (1 - ((distance - max_distance) / (min_distance - max_distance))) * 10;
           result.score += (int)distance_result;
        }

        double max_time = score.getMaxDrivingTime() * 60 * 60; //convert to seconds
        double time = distancematrix.rows.get(0).elements.get(0).duration_in_traffic.value;
        double time_result = time / max_time;
        if(time_result > 1){
            result.recomendet = false;
            result.recomendet_errors.add("Destination takes to long to drive to");
        }else{
            double min_time = 10 * 60; //10 minuit benchmark for minimum;
            time_result = (1 - ((time - max_time) / (min_time - max_time))) * 10;
            result.score += (int)time_result;
        }

    }
}

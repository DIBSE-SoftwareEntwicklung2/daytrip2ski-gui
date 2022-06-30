package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Score;
import com.example.application.dto.Skiresort;

import java.time.LocalDate;
import java.time.LocalTime;

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

        resolveTime(person,skiresort,result,score);

        resolveBooleans(person, skiresort, result, score);

        resolveBudged(person, skiresort, result, score);

        resolveSlopeDistance(person, skiresort, result, score);

        return result;
    }

    static private void resolveTime(Person person, Skiresort skiresort, Result result, Score score){
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

    static private void resolveBooleans(Person person, Skiresort skiresort, Result result, Score score){
        if(score.getRequiresRental() && !skiresort.getSkiRental()){
            result.recomendet = false;
            result.recomendet_errors.add("Does not have Rental");
        }
        if(score.getRequiresFamilyFriendly() && !skiresort.getFamilyFriendly()){
            result.recomendet = false;
            result.recomendet_errors.add("Is not Family friendly");
        }
    }

    static private void resolveBudged(Person person, Skiresort skiresort, Result result, Score score){
        /*
        i am also assuming right now that only one adult is going for one day
         */
        if(score.getBudged() < skiresort.getPriceDayTicketAdults()){
            result.recomendet = false;
            result.recomendet_errors.add("Does not fit Budget");
        }
    }

    static private void resolveSlopeDistance(Person person, Skiresort skiresort, Result result, Score score){
        RestSkiresortService getStats = new RestSkiresortService();

        Long easy_min = getStats.getmindistanceeasy().get(0).getDistanceEasy();
        Long easy_max = getStats.getmaxdistanceeasy().get(0).getDistanceEasy();

        Long intermediate_min = getStats.getmindistanceintermediate().get(0).getDistanceIntermediate();
        Long intermediate_max = getStats.getmaxdistanceintermediate().get(0).getDistanceIntermediate();

        Long difficult_min = getStats.getmindistancedifficult().get(0).getDistanceDifficult();
        Long difficult_max = getStats.getmaxdistancedifficult().get(0).getDistanceDifficult();


        //scale results to a value between 0 and 1
        Double scaled_easy;
        if(easy_min != easy_max){
           scaled_easy = (skiresort.getDistanceEasy().doubleValue() - easy_min.doubleValue()) / (easy_max.doubleValue() - easy_min.doubleValue());
        }else{
           scaled_easy = 1d;
        }
        Double scaled_intermediate;
        if(intermediate_min != intermediate_max){
           scaled_intermediate = (skiresort.getDistanceIntermediate().doubleValue() - intermediate_min.doubleValue()) / (intermediate_max.doubleValue() - intermediate_min.doubleValue());
        }else{
           scaled_intermediate = 1d;
        }
        Double scaled_difficult;
        if(difficult_min != difficult_max){
            scaled_difficult = (skiresort.getDistanceDifficult().doubleValue() - difficult_min.doubleValue()) / (difficult_max.doubleValue() - difficult_min.doubleValue());
        }else{
            scaled_difficult = 1d;
        }

        //evaluate how close the values are and transform them to a value between 0 and 10
        Double easy_result = abs(score.getAffinityToEasyTracks() - scaled_easy);
        easy_result = (1 - easy_result) * 10;
        Double intermediate_result = abs(score.getAffinityToIntermediateTracks() - scaled_intermediate);
        intermediate_result = (1 - intermediate_result) * 10;
        Double difficult_result = abs(score.getAffinityToDifficultTracks() - scaled_difficult);
        difficult_result = (1 - difficult_result) * 10;

        result.score += easy_result.intValue();
        result.score += intermediate_result.intValue();
        result.score += difficult_result.intValue();
    }
}

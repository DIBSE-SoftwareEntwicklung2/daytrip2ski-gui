package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Score;
import com.example.application.dto.Skiresort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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
        double ammount_different_lifts = 0D;
        double total_ammount = 0D;

        if(skiresort.getNumberOfBabyLift() != 0)        {total_ammount += skiresort.getNumberOfBabyLift();       ammount_different_lifts++;}
        if(skiresort.getNumberOfCableCar() != 0)        {total_ammount += skiresort.getNumberOfCableCar();       ammount_different_lifts++;}
        if(skiresort.getNumberOfChairLift() != 0)       {total_ammount += skiresort.getNumberOfChairLift();      ammount_different_lifts++;}
        if(skiresort.getNumberOfCogRailway() != 0)      {total_ammount += skiresort.getNumberOfCogRailway();     ammount_different_lifts++;}
        if(skiresort.getNumberOfFunicular() != 0)       {total_ammount += skiresort.getNumberOfFunicular();      ammount_different_lifts++;}
        if(skiresort.getNumberOfGondolaLift() != 0)     {total_ammount += skiresort.getNumberOfGondolaLift();    ammount_different_lifts++;}
        if(skiresort.getNumberOfMovingCarpet() != 0)    {total_ammount += skiresort.getNumberOfMovingCarpet();   ammount_different_lifts++;}
        if(skiresort.getNumberOfTBarLift() != 0)        {total_ammount += skiresort.getNumberOfTBarLift();       ammount_different_lifts++;}

        //in this cas min and max is reversed because the smaller the value is the better it is for us
        double min = total_ammount;
        double max = total_ammount / 8;
        double current = total_ammount / ammount_different_lifts;

        //we prob need staats from the database to make a better guess
        double variety_result = (current - min) / (max - min);
        variety_result = abs(score.getVariety() - variety_result);
        variety_result =  (1 - variety_result) * 10;
        result.score += (int)variety_result;
    }
}

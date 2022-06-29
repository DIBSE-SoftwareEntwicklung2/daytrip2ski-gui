package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.Result;
import com.example.application.dto.Score;
import com.example.application.dto.Skiresort;

import java.time.LocalDate;
import java.time.LocalTime;

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


        if(score.getRequiresRental() && !skiresort.getSkiRental()){
            result.recomendet = false;
            result.recomendet_errors.add("Does not have Rental");
        }
        if(score.getRequiresFamilyFriendly() && !skiresort.getFamilyFriendly()){
            result.recomendet = false;
            result.recomendet_errors.add("Is not Family friendly");
        }

        /*
        i am also assuming right now that only one adult is going for one day
         */
        if(score.getBudged() < skiresort.getPriceDayTicketAdults()){
            result.recomendet = false;
            result.recomendet_errors.add("Does not fit Budget");
        }


        return result;
    }
}

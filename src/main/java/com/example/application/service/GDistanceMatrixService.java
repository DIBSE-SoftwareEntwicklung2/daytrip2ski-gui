package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.ResultDistanceMatrix;
import com.example.application.dto.Skiresort;

public class GDistanceMatrixService {
    public static ResultDistanceMatrix getDistanceMatrix(Person person, Skiresort skiresort){
        ResultDistanceMatrix result = new ResultDistanceMatrix();
        /*
        example url
        https://maps.googleapis.com/maps/api/distancematrix/json
        ?departure_time=now
        &destinations=Lexington%2CMA%7CConcord%2CMA
        &origins=Boston%2CMA%7CCharlestown%2CMA
        &key=YOUR_API_KEY
         */
        return result;
    }
}

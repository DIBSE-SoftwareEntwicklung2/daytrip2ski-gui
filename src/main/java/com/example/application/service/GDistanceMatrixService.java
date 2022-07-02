package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.ResultDistanceMatrix;
import com.example.application.dto.Skiresort;
import org.springframework.web.reactive.function.client.WebClient;


public class GDistanceMatrixService {
    public static ResultDistanceMatrix getDistanceMatrix(Person person, Skiresort skiresort){
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("https://maps.googleapis.com/maps/api/distancematrix/json?departure_time=now&destinations="
                                                                                + skiresort.getLatitude() + "," + skiresort.getLongitude() + "&origins=" +  person.getHomeLatitude()+ "," + person.getHomeLongitude()
                                                                                + "&key=" + System.getenv("GOOGLE_MATRIX_API"));
        ResultDistanceMatrix result = spec.retrieve().toEntity(ResultDistanceMatrix.class).block().getBody();
//        System.out.println("https://maps.googleapis.com/maps/api/distancematrix/json?departure_time=now&destinations="
//                + skiresort.getLatitude() + "," + skiresort.getLongitude() + "&origins" +  person.getHomeLatitude()+ "," + person.getHomeLongitude()
//                + "&key=");
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

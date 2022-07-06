package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.ResultDistanceMatrix;
import com.example.application.dto.Skiresort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * This class is utilized to recieve an api call result from the google distance matrix api
 */
@Service
public class GDistanceMatrixService {
    /**
     * calls to the google distance matrix api with the lat/lon of a person and a Skiresort
     *
     * @param person
     * @param skiresort
     * @return ResultDistanceMatrix Object
     */
    public ResultDistanceMatrix getDistanceMatrix(Person person, Skiresort skiresort) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("https://maps.googleapis.com/maps/api/distancematrix/json?departure_time=now&destinations=" + skiresort.getLatitude() + "," + skiresort.getLongitude() + "&origins=" + person.getHomeLatitude() + "," + person.getHomeLongitude() + "&key=" + System.getenv("GOOGLE_MATRIX_API"));
        return Objects.requireNonNull(spec.retrieve().toEntity(ResultDistanceMatrix.class).block()).getBody();
    }
}

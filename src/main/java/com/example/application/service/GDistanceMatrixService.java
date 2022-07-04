package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.ResultDistanceMatrix;
import com.example.application.dto.Skiresort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class GDistanceMatrixService {
    public ResultDistanceMatrix getDistanceMatrix(Person person, Skiresort skiresort) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("https://maps.googleapis.com/maps/api/distancematrix/json?departure_time=now&destinations=" + skiresort.getLatitude() + "," + skiresort.getLongitude() + "&origins=" + person.getHomeLatitude() + "," + person.getHomeLongitude() + "&key=" + System.getenv("GOOGLE_MATRIX_API"));
        return Objects.requireNonNull(spec.retrieve().toEntity(ResultDistanceMatrix.class).block()).getBody();
    }
}

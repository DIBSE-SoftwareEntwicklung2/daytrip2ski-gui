package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RestSkiresortService {

    public List<Skiresort> getallSkiresorts() {
        return WebClient.create().get().uri("http://localhost:8081/api/v1/skiresort").retrieve().toEntityList(Skiresort.class).block().getBody();
    }

    public Skiresort getOne(Long id) {
        return WebClient.create().get().uri("http://localhost:8081/api/v1/skiresort/"+id).retrieve().toEntity(Skiresort.class).block().getBody();
    }

}

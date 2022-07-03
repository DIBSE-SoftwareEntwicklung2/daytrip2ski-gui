package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class RestSkiresortService {
    private static final String BASE_PATH = System.getenv("BASE_URL");
    private static final String EXTENSION = "api/v1/skiresort/";


    public List<Skiresort> getAllSkiresorts() {
        //System.out.println("=========================================================");
        //System.out.println("getAllSkiresorts");
        //System.out.println("Base Path :" + BASE_PATH + "EXTENSION:" +EXTENSION);

        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION);
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public Skiresort getOne(Long id) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + id);
        return Objects.requireNonNull(spec.retrieve().toEntity(Skiresort.class).block()).getBody();
    }

    public List<Skiresort> getMaxDistanceEasy() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxdistanceeasy");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public List<Skiresort> getMinDistanceEasy() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "mindistanceeasy");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public List<Skiresort> getMaxDistanceIntermediate() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxdistanceintermediate");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public List<Skiresort> getMinDistanceIntermediate() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "mindistanceintermediate");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public List<Skiresort> getMaxDistanceDifficult() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxdistancedifficult");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public List<Skiresort> getMinDistanceDifficult() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "mindistancedifficult");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    public Long getMaxNumbersOfClimbingAids() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxnumbersofclimbingaids");
        return Objects.requireNonNull(spec.retrieve().toEntity(Long.class).block()).getBody();
    }

    public Long getMinNumbersOfClimbingAids() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "minnumbersofclimbingaids");
        return Objects.requireNonNull(spec.retrieve().toEntity(Long.class).block()).getBody();
    }
}

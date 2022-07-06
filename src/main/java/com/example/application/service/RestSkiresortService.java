package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

/**
 * This Class handles all Backend to Frontend communication regarding Skiresorts
 */
@Service
public class RestSkiresortService {
    private static final String BASE_PATH = System.getenv("BASE_URL");
    private static final String EXTENSION = "api/v1/skiresort/";

    /**
     * this function returns all Skiresorts
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getAllSkiresorts() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION);
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * this function returns a Skiresort recognized by its Id
     *
     * @param id ID
     * @return Skiresort
     */
    public Skiresort getOne(Long id) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + id);
        return Objects.requireNonNull(spec.retrieve().toEntity(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with maxDistanceEasy
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getMaxDistanceEasy() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxdistanceeasy");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MinDistanceEasy
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getMinDistanceEasy() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "mindistanceeasy");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MaxDistanceIntermediate
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getMaxDistanceIntermediate() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxdistanceintermediate");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MinDistanceIntermediate
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getMinDistanceIntermediate() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "mindistanceintermediate");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MaxDistanceDifficult
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getMaxDistanceDifficult() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxdistancedifficult");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MinDistanceDifficult
     *
     * @return List<Skiresort>
     */
    public List<Skiresort> getMinDistanceDifficult() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "mindistancedifficult");
        return Objects.requireNonNull(spec.retrieve().toEntityList(Skiresort.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MaxNumbersOfClimbingAids
     *
     * @return List<Skiresort>
     */
    public Long getMaxNumbersOfClimbingAids() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "maxnumbersofclimbingaids");
        return Objects.requireNonNull(spec.retrieve().toEntity(Long.class).block()).getBody();
    }

    /**
     * returns the Skiresort with MinNumbersOfClimbingAids
     *
     * @return List<Skiresort>
     */
    public Long getMinNumbersOfClimbingAids() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "minnumbersofclimbingaids");
        return Objects.requireNonNull(spec.retrieve().toEntity(Long.class).block()).getBody();
    }
}

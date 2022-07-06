package com.example.application.service;

import com.example.application.dto.Person;
import com.example.application.dto.Score;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * This class handles all communication between frontend and backend regarding persons
 */
@Service
public class RestPersonService {
    private static final String BASE_PATH = System.getenv("BASE_URL");
    private static final String EXTENSION = "api/v1/person/";

    /**
     * this function returns all persons saved in the database
     *
     * @return List<Person>
     */
    public List<Person> getAllPersons() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION);
        return Objects.requireNonNull(spec.retrieve().toEntityList(Person.class).block()).getBody();
    }

    /**
     * this function returns a Person by its Id
     *
     * @param id ID
     * @return Person
     */
    public Person getPersonById(Long id) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + id);
        return Objects.requireNonNull(spec.retrieve().toEntity(Person.class).block()).getBody();
    }

    /**
     * this function return only the Score of a person recognized by its id
     *
     * @param id ID
     * @return Score
     */
    public Score getScoreFromPerson(Long id) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "score/" + id);
        Score score = Objects.requireNonNull(spec.retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).toEntity(Score.class).block()).getBody();
        if ((score != null ? score.getId() : null) == null) {
            score = null;
        }
        return score;
    }

    /**
     * this function can register a new Person in the backend
     *
     * @param person Person
     */
    public void postRegisterPerson(Person person) {
        Mono<Person> mono = WebClient.create().post().uri(BASE_PATH + EXTENSION + "register").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(person), Person.class).retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).bodyToMono(Person.class);
        mono.block();
    }

    /**
     * this function updates a Person in the backend
     *
     * @param person Person
     */
    public void savePerson(Person person) {
        Mono<Person> mono = WebClient.create().post().uri(BASE_PATH + EXTENSION + "save").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(person), Person.class).retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).bodyToMono(Person.class);
        mono.block();
    }
}

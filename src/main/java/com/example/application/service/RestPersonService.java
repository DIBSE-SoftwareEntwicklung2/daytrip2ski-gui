package com.example.application.service;

import java.util.List;
import java.util.Objects;

import com.example.application.dto.Person;
import com.example.application.dto.Score;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestPersonService {
    private static final String BASE_PATH = System.getenv("BASE_URL");
    private static final String EXTENSION = "api/v1/person/";

    public List<Person> getAllPersons() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION);

        return Objects.requireNonNull(spec.retrieve().toEntityList(Person.class).block()).getBody();
    }

    public Person getPersonById(int id) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + id);

        return Objects.requireNonNull(spec.retrieve().toEntity(Person.class).block()).getBody();
    }

    public Score getScoreFromPerson(Long id) {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(BASE_PATH + EXTENSION + "score/" + id);

        Score score = Objects.requireNonNull(spec.retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).toEntity(Score.class).block()).getBody();
        if ((score != null ? score.getId() : null) == null) {
            score = null;
        }
        return score;
    }

    public void postRegisterPerson(Person person) {
        Mono<Person> mono = WebClient.create().post().uri(BASE_PATH + EXTENSION + "register").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(person), Person.class).retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).bodyToMono(Person.class);
        mono.block();
    }

    public void savePerson(Person person) {
        Mono<Person> mono = WebClient.create().post().uri(BASE_PATH + EXTENSION + "save").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(person), Person.class).retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).bodyToMono(Person.class);
        mono.block();
    }
}

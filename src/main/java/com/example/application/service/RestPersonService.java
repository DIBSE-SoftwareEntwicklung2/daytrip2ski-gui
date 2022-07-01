package com.example.application.service;

import java.util.List;

import com.example.application.dto.Person;
import com.example.application.dto.Score;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class RestPersonService {
    private String basepath = "http://localhost:8081/";
    private String extension = "api/v1/person/";

    public List<Person> getallPersons() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(basepath + extension);

        List<Person> ppl = spec.retrieve().toEntityList(Person.class).block().getBody();
        return ppl;
    }

    public Person getPersonbyId(int id){
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(basepath + extension + id);

        Person person = spec.retrieve().toEntity(Person.class).block().getBody();
        return person;
    }

    public Score getScorefromPerson(Person person){
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri(basepath + extension + "score/" + person.getId());

        Score score = spec.retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).toEntity(Score.class).block().getBody();
        if(score.getId() == null) score = null;
        return score;
    }

    public void postRegisterPerson(Person person){
        Mono<Person> mono = WebClient.create().post().uri(basepath + extension + "register").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(person), Person.class).retrieve()
                .onStatus(status -> status.value() == 500, clientResponse -> Mono.empty())
                .bodyToMono(Person.class);
        mono.block();
    }

    public void savePerson(Person person){
        Mono<Person> mono = WebClient.create().post().uri(basepath + extension + "save").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(Mono.just(person), Person.class).retrieve()
                .onStatus(status -> status.value() == 500, clientResponse -> Mono.empty())
                .bodyToMono(Person.class);
        mono.block();
    }

}

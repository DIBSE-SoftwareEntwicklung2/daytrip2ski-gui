package com.example.application.service;

import java.util.List;

import com.example.application.dto.Person;
import com.example.application.dto.Score;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class RestPersonService {

    public List<Person> getallPersons() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("http://localhost:8081/api/v1/person");

        List<Person> ppl = spec.retrieve().toEntityList(Person.class).block().getBody();
        return ppl;
    }

    public Person getPersonbyId(int id){
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("http://localhost:8081/api/v1/person/" + id);

        Person person = spec.retrieve().toEntity(Person.class).block().getBody();
        return person;
    }

    public Score getScorefromPerson(Person person){
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("http://localhost:8081/api/v1/person/score/" + person.getId());

        Score score = spec.retrieve().onStatus(status -> status.value() == 500, clientResponse -> Mono.empty()).toEntity(Score.class).block().getBody();
        if(score.getId() == null) score = null;
        return score;
    }

//    public void postRegisterPerson(Person person){
//        WebClient.RequestHeadersSpec<?> spec = WebClient.create().post().uri("http://localhost:8081/api/v1/person/register").body(BodyInserters.fromValue(person));
//    }

}

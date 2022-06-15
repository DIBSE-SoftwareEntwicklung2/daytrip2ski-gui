package com.example.application.service;

import java.util.List;

import com.example.application.dto.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class RestPersonService {

    public void test() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("http://localhost:8081/api/v1/person");

        List<Person> ppl = spec.retrieve().toEntityList(Person.class).block().getBody();

        System.out.println(String.format("...received %d items.", ppl.size()));
        System.out.println(ppl.get(0).toString());
        System.out.println(ppl.get(1).toString());
    }





}

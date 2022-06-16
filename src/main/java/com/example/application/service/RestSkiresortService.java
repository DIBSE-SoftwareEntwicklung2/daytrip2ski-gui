package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RestSkiresortService {

        public void test() {
            WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri("http://localhost:8081/api/v1/skiresort");

            List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();

            System.out.println(String.format("...received %d items.", skiresorts.size()));
            System.out.println(skiresorts.get(0).toString());
            System.out.println(skiresorts.get(1).toString());
        }





    }
}

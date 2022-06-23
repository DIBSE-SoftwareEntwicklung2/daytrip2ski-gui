package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RestSkiresortService {

//    @Autowired
//    private Environment env;



        public List<Skiresort> getallSkiresorts() {
            //System.out.println( env.getProperty("api.baseurl"));
            WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( "http://localhost:8081/api/v1/skiresort");
            //env.getProperty("api.baseurl") + "skiresort"

            List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
            return skiresorts;
        }

}

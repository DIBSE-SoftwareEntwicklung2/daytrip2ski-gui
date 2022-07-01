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
private String basepath = "http://localhost:8081/";
    private String extension = "api/v1/skiresort/";


        public List<Skiresort> getallSkiresorts() {
            WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension);


            List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
            return skiresorts;
        }

    public List<Skiresort> getmaxdistanceeasy() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "maxdistanceeasy");
        List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
        return skiresorts;
    }
    public List<Skiresort> getmindistanceeasy() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "mindistanceeasy");
        List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
        return skiresorts;
    }
    public List<Skiresort> getmaxdistanceintermediate() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "maxdistanceintermediate");
        List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
        return skiresorts;
    }
    public List<Skiresort> getmindistanceintermediate() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "mindistanceintermediate");
        List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
        return skiresorts;
    }
    public List<Skiresort> getmaxdistancedifficult() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "maxdistancedifficult");
        List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
        return skiresorts;
    }
    public List<Skiresort> getmindistancedifficult() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "mindistancedifficult");
        List<Skiresort> skiresorts = spec.retrieve().toEntityList(Skiresort.class).block().getBody();
        return skiresorts;
    }

    public Long getmaxnumbersofclimbingaids() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "maxnumbersofclimbingaids");
        Long result = spec.retrieve().toEntity(Long.class).block().getBody();
        return result;
    }

    public Long getminnumbersofclimbingaids() {
        WebClient.RequestHeadersSpec<?> spec = WebClient.create().get().uri( basepath + extension + "minnumbersofclimbingaids");
        Long result = spec.retrieve().toEntity(Long.class).block().getBody();
        return result;
    }


}

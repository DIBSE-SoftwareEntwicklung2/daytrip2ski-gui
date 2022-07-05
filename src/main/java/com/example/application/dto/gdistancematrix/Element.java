package com.example.application.dto.gdistancematrix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Element {
    private Distance distance;
    private Duration duration;
    private DurationInTraffic durationInTraffic;
    private String status;
}

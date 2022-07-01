package com.example.application.dto.gdistancematrix;

import lombok.ToString;

@ToString
public class Element {
    public Distance distance;
    public Duration duration;
    public DurationInTraffic duration_in_traffic;
    public String status;
}

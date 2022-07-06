package com.example.application.dto.gdistancematrix;

import lombok.ToString;

@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class Element {
    public Distance distance;
    public Duration duration;
    @SuppressWarnings("squid:S116") // Api returns it like this.
    public DurationInTraffic duration_in_traffic;
    public String status;
}

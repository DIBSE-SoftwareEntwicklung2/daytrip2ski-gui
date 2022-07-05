package com.example.application.dto.gdistancematrix;

import lombok.ToString;

@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class DurationInTraffic {
    public String text;
    public int value;
}

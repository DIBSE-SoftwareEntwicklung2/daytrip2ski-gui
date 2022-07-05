package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class Coord {
    public double lon;
    public double lat;
}

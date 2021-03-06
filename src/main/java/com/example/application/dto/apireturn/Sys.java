package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class Sys {
    public int type;
    public int id;
    public double message;
    public String country;
    public int sunrise;
    public int sunset;
}

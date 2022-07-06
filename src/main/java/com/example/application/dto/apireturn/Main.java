package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class Main {
    public double temp;
    @SuppressWarnings("squid:S116") // Api returns it like this.
    public double feels_like;
    @SuppressWarnings("squid:S116") // Api returns it like this.
    public double temp_min;
    @SuppressWarnings("squid:S116") // Api returns it like this.
    public double temp_max;
    public int pressure;
    public int humidity;
}

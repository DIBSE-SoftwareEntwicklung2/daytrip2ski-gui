package com.example.application.dto.apireturn;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class FeelsLike {
    public double day;
    public double night;
    public double eve;
    public double morn;
}

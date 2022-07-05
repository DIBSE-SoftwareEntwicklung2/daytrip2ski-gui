package com.example.application.dto.apireturn;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class FeelsLike {
    private double day;
    private double night;
    private double eve;
    private double morn;
}

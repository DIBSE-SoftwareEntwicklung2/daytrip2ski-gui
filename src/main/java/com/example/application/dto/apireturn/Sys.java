package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Sys {
    private int type;
    private int id;
    private double message;
    private String country;
    private int sunrise;
    private int sunset;
}

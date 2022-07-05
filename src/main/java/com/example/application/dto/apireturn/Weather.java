package com.example.application.dto.apireturn;

import lombok.Getter;

@Getter
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}

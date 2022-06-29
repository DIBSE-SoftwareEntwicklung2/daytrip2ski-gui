package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Weather{
    public int id;
    public String main;
    public String description;
    public String icon;
}

package com.example.application.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;


@ToString
public class Result {
    public boolean valid;
    public String valid_error;

    public int score;

    public boolean recomendet;
    public List<String> recomendet_errors;

    public Result(){
        this.valid = false;
        this.valid_error = "";

        this.score = 0;

        this.recomendet = true;
        this.recomendet_errors = new ArrayList<>();
    }
}

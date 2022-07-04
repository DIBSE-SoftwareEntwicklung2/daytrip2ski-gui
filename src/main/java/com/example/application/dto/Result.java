package com.example.application.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@ToString
public class Result {
    public boolean valid;
    public String valid_error;
    public int score;
    public boolean recommended;
    public List<String> recommended_errors;

    public Result() {
        this.valid = false;
        this.valid_error = "";
        this.score = 0;
        this.recommended = true;
        this.recommended_errors = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Result)) {
            return false;
        }

        Result r = (Result) o;
        // Simple equality check.
        if (Boolean.compare(valid, r.valid) != 0 ||
                Boolean.compare(recommended, r.recommended) != 0 ||
                !valid_error.equals(r.valid_error) ||
                score != r.score ||
                recommended_errors.size() != r.recommended_errors.size()) {
            return false;
        }

        // ArrayList String compare.
        for (int i = 0; i < recommended_errors.size(); i++) {
            if (!recommended_errors.get(i).equals(r.recommended_errors.get(0))) {
                return false;
            }
        }

        return true;
    }
}

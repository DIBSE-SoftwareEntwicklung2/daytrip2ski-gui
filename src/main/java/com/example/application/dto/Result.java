package com.example.application.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

/**
 * Object that hold the result of an evaluation between Person and Skiresort by an ScoreEvaluator
 *
 * has auto generated getter and setters aswell as a ToString method and an All args Constructor
 *
 * boolean valid is false if it could not generate a Score also has an error message in valid Error
 *
 * int Score hold a result if valid = true
 * even if a score exist we might not recommend it represented by boolean recommended
 * List<String> recommendedErrors holds all given Errors if recommendet is false
 */
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Result {
    private boolean valid;
    private String validError;
    private int score;
    private boolean recommended;
    private List<String> recommendedErrors;

    /**
     * Constructor to create an empty result Object
     */
    public Result() {
        this.valid = false;
        this.validError = "";
        this.score = 0;
        this.recommended = true;
        this.recommendedErrors = new ArrayList<>();
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
        if (Boolean.compare(valid, r.valid) != 0 || Boolean.compare(recommended, r.recommended) != 0 || !validError.equals(r.validError) || score != r.score || recommendedErrors.size() != r.recommendedErrors.size()) {
            return false;
        }

        // ArrayList String compare.
        for (int i = 0; i < recommendedErrors.size(); i++) {
            if (!recommendedErrors.get(i).equals(r.recommendedErrors.get(0))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (valid ? 1 : 0);
        result = 31 * result + (recommended ? 1 : 0);
        result = 31 * result + score;
        result = 31 * result + validError.hashCode();
        result = 31 * result + recommendedErrors.hashCode();
        return result;
    }
}

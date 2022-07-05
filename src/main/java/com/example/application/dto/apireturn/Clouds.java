package com.example.application.dto.apireturn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Clouds {
    @SuppressWarnings("squid:S1104") // We need public variables for the api return.
    public int all;
}

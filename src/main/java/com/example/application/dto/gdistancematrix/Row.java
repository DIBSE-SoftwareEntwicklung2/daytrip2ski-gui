package com.example.application.dto.gdistancematrix;

import lombok.ToString;

import java.util.List;

@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class Row {
    public List<Element> elements;
}

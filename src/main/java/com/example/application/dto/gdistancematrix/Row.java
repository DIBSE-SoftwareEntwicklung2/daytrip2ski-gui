package com.example.application.dto.gdistancematrix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Row {
    private List<Element> elements;
}

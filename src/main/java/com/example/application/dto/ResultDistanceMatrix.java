package com.example.application.dto;

import com.example.application.dto.gdistancematrix.Row;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class ResultDistanceMatrix {
    public ArrayList<String> destination_addresses;
    public ArrayList<String> origin_addresses;
    public ArrayList<Row> rows;
    public String status;
}

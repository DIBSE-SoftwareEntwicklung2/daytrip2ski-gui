package com.example.application.dto;

import com.example.application.dto.gdistancematrix.Row;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class ResultDistanceMatrix {
    public ArrayList<String> destination_addresses;
    public ArrayList<String> origin_addresses;
    public ArrayList<Row> rows;
    public String status;
}

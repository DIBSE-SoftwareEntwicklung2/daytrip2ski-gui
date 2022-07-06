package com.example.application.dto;

import com.example.application.dto.gdistancematrix.Row;
import lombok.ToString;

import java.util.List;

@ToString
@SuppressWarnings("squid:S1104") // We need public variables for the api return.
public class ResultDistanceMatrix {
    @SuppressWarnings("squid:S116") // Api returns it like this.
    public List<String> destination_addresses;
    @SuppressWarnings("squid:S116") // Api returns it like this.
    public List<String> origin_addresses;
    public List<Row> rows;
    public String status;
}

package com.example.application.dto;

import com.example.application.dto.gdistancematrix.Row;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResultDistanceMatrix {
    private List<String> destinationAddresses;
    private List<String> originAddresses;
    private List<Row> rows;
    private String status;
}

package com.example.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Score {

    private Long id;

    private Double variety;
    private Double affinityToEasyTracks;
    private Double affinityToIntermediateTracks;
    private Double affinityToDifficultTracks;
    private Boolean requiresRental;
    private Boolean requiresFamilyFriendly;
    private Double budged;
    private Double maxDistance;
    private Double maxDrivingTime;
}

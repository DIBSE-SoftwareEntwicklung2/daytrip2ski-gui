package com.example.application.dto;

import lombok.*;


/**
 * Stores the Score of Users utilized to determined how Skiresorts fit to the user
 *
 * auto generated getters and setters aswell as a NoArgsConstructor, AllArgsConstructor and a ToString method
 */
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

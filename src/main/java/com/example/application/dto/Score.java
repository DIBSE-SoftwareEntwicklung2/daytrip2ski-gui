package com.example.application.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message="Budget is required")
    @Min(0)
    @Max(10000)
    private Double budged;
    @NotNull(message="Max Distance is required")
    @Min(0)
    private Double maxDistance;
    @NotNull(message="Max Driving Time is required")
    @Min(0)
    private Double maxDrivingTime;
}

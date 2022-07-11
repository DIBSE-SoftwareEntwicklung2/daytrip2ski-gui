package com.example.application.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testing score class
 */
class ScoreTest {
    /**
     * Creation of score instance
     */
    @Test
    void createScoreSuccessful() {
        var score = new Score(1L, 2d, 3d, 4d, 5d, true, true, 6d, 7d, 8d);
        Assertions.assertEquals(1L, score.getId());
        Assertions.assertEquals(2d, score.getVariety());
        Assertions.assertEquals(3d, score.getAffinityToEasyTracks());
        Assertions.assertEquals(4d, score.getAffinityToIntermediateTracks());
        Assertions.assertEquals(5d, score.getAffinityToDifficultTracks());
        Assertions.assertEquals(true, score.getRequiresRental());
        Assertions.assertEquals(true, score.getRequiresFamilyFriendly());
        Assertions.assertEquals(6d, score.getBudged());
        Assertions.assertEquals(7d, score.getMaxDistance());
        Assertions.assertEquals(8d, score.getMaxDrivingTime());
    }
}
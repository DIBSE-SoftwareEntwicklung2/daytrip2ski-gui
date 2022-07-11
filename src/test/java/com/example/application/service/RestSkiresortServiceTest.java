package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Testing rest service of skiresort
 */
class RestSkiresortServiceTest {
    RestSkiresortService restSkiresortService = new RestSkiresortService();

    /**
     * Just to see that it works - getting all existing skiresorts
     */
    @Test
    void getAllSkiresorts() {
        List<Skiresort> skiResorts = this.restSkiresortService.getAllSkiresorts();
        Assertions.assertFalse(skiResorts.isEmpty());
    }
}
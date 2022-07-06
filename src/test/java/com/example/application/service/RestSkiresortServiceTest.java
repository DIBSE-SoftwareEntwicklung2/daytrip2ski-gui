package com.example.application.service;

import com.example.application.dto.Skiresort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RestSkiresortServiceTest {
    RestSkiresortService restSkiresortService = new RestSkiresortService();

    @Test
    void getAllSkiresorts() {
        List<Skiresort> skiResorts = this.restSkiresortService.getAllSkiresorts();
        Assertions.assertFalse(skiResorts.isEmpty());
    }
}
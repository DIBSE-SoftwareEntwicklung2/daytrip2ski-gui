package com.example.application.service;

import com.example.application.dto.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Testing rest service of person
 */
class RestPersonServiceTest {
    RestPersonService restPersonService = new RestPersonService();

    /**
     * Just to see that it works - getting all existing persons
     */
    @Test
    void getAllPersons() {
        List<Person> person = this.restPersonService.getAllPersons();
        Assertions.assertFalse(person.isEmpty());
    }
}
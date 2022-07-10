package com.example.application.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.Period;

/**
 * Testing the whole persons class.
 * Day of birth not really testable because LocalDate.of() is not allowing wrong dates.
 */
class PersonTest {
    private static Validator validator;

    @BeforeAll
    public static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * Test creating a successful person without score.
     */
    @Test
    void createPersonSuccessful() {
        var person = new Person("Max", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 7.8, 8.9);
        Assertions.assertEquals("Max", person.getFirstName());
        Assertions.assertEquals("Mustermann", person.getLastName());
        Assertions.assertEquals("max.mustermann@test.com", person.getEmail());
        Assertions.assertEquals(LocalDate.of(1999, 1, 8), person.getDob());
        Assertions.assertNull(person.getScore());

        var violations = validator.validate(person);
        Assertions.assertTrue(violations.isEmpty(), violations.toString());
    }

    /**
     * Test creating a successful person with score.
     */
    @Test
    void createPersonSuccessfulWithScore() {
        var score = new Score(1L, 0d, 0d, 0d, 0d, false, false, 0d, 0d, 0d);
        var person = new Person("Max", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 7.8, 8.9, score);
        Assertions.assertEquals("Max", person.getFirstName());
        Assertions.assertEquals("Mustermann", person.getLastName());
        Assertions.assertEquals("max.mustermann@test.com", person.getEmail());
        Assertions.assertEquals(LocalDate.of(1999, 1, 8), person.getDob());
        Assertions.assertEquals(score, person.getScore());

        var violations = validator.validate(person);
        Assertions.assertTrue(violations.isEmpty(), violations.toString());
    }

    /**
     * Test creating a successful person with wrong email address.
     */
    @Test
    void createPersonWrongEmail() {
        var person = new Person("Max", "Mustermann", "max.mustermanntest.com", LocalDate.of(1999, 1, 8), 7.8, 8.9);
        var violations = validator.validate(person);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size(), "Only Email should be invalid: " + violations);
        var first = violations.iterator().next();
        Assertions.assertEquals("Not a valid E-Mail address", first.getMessage());
    }

    /**
     * Test creating a successful person with wrong first name.
     */
    @Test
    void createPersonWrongFirstName() {
        var person = new Person("", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 7.8, 8.9);
        var violations = validator.validate(person);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size(), "Only FirstName should be invalid: " + violations);
        var first = violations.iterator().next();
        Assertions.assertEquals("First Name is required", first.getMessage());
    }

    /**
     * Test creating a successful person with wrong last name.
     */
    @Test
    void createPersonWrongLastName() {
        var person = new Person("Max", "", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), 7.8, 8.9);
        var violations = validator.validate(person);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size(), "Only LastName should be invalid: " + violations);
        var first = violations.iterator().next();
        Assertions.assertEquals("Last Name is required", first.getMessage());
    }

    /**
     * Test creating a successful person with wrong home positions.
     */
    @Test
    void createPersonNoHomePosition() {
        var person = new Person("Max", "Mustermann", "max.mustermann@test.com", LocalDate.of(1999, 1, 8), null, null);
        var violations = validator.validate(person);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(2, violations.size(), "Only home positions should be invalid: " + violations);
        var first = violations.iterator().next();
        Assertions.assertEquals("Home position required", first.getMessage());
        var second = violations.iterator().next();
        Assertions.assertEquals("Home position required", second.getMessage());
    }

    /**
     * Test transient get persons age method.
     */
    @Test
    void getPersonsAge() {
        // Arrange
        var date = LocalDate.now().minusYears(20).minusDays(1);
        var date2 = LocalDate.now().minusYears(20).plusDays(1);
        var person = new Person("Max", "Mustermann", "max.mustermann@test.com", date, 7.8, 8.9);
        var person2 = new Person("Max", "Mustermann", "max.mustermann@test.com", date2, 7.8, 8.9);

        // Act
        var actualAge = person.getAge();
        var actualAge2 = person2.getAge();

        var expectedAge = Period.between(date, LocalDate.now()).getYears();
        var expectedAge2 = Period.between(date2, LocalDate.now()).getYears();

        // Assert
        Assertions.assertEquals(expectedAge, actualAge);
        Assertions.assertEquals(expectedAge2, actualAge2);
    }

    /**
     * Test person to string convert.
     */
    @Test
    void personToString() {
        var date = LocalDate.now().minusYears(20).minusDays(1);
        Person person = new Person("Max", "Mustermann", "max.mustermann@test.com", date, 7.8, 8.9);
        person.setId(1L);
        var expectedAge = Period.between(date, LocalDate.now()).getYears();
        String personString = person.toString();
        Assertions.assertEquals("Person(id=1, firstName=Max, lastName=Mustermann, email=max.mustermann@test.com, dob=" + date + ", homeLatitude=7.8, homeLongitude=8.9, score=null, age=" + expectedAge + ")", personString);
    }
}

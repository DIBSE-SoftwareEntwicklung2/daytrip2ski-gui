package com.example.application.dto;

import java.time.LocalDate;
import java.time.Period;

import lombok.*;


/**
 * Class that represents a Person
 * has auto generated getters and setters aswell as a ToString method, AllArgsConstructor and no ArgsConstructor
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private Score score;
    private Integer age;
    private Double homeLatitude = 47.269211;
    private Double homeLongitude = 11.404102;

    public String getIdStr() {
        return id.toString();
    }

    /**
     * Smaller Constructor without Id and Score object
     * @param firstName
     * @param lastName
     * @param email
     * @param dob
     * @param homeLatitude
     * @param homeLongitude
     */
    public Person(String firstName, String lastName, String email, LocalDate dob, Double homeLatitude, double homeLongitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.homeLatitude = homeLatitude;
        this.homeLongitude = homeLongitude;
    }

    /**
     * getAge returns the age of a Person
     * @return Integer
     */
    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
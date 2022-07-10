package com.example.application.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;

/**
 * Class that represents a Person
 * has auto generated getters and setters as well as a ToString method, AllArgsConstructor and no ArgsConstructor
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private Long id;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Not a valid E-Mail address")
    private String email;

    @NotNull
    private LocalDate dob;

    @NotNull(message = "Home position required")
    private Double homeLatitude = 47.269211;

    @NotNull(message = "Home position required")
    private Double homeLongitude = 11.404102;

    @Valid
    private Score score;

    private Integer age;

    /**
     * Smaller Constructor without id and Score object
     *
     * @param firstName     first name
     * @param lastName      last name
     * @param email         email
     * @param dob           day of birth
     * @param homeLatitude  home location latitude
     * @param homeLongitude home location longitude
     */
    public Person(String firstName, String lastName, String email, LocalDate dob, Double homeLatitude, Double homeLongitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.homeLatitude = homeLatitude;
        this.homeLongitude = homeLongitude;
    }

    /**
     * Smaller Constructor without id
     *
     * @param firstName     first name
     * @param lastName      last name
     * @param email         email
     * @param dob           day of birth
     * @param homeLatitude  home location latitude
     * @param homeLongitude home location longitude
     * @param score         score
     */
    public Person(String firstName, String lastName, String email, LocalDate dob, Double homeLatitude, Double homeLongitude, Score score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.homeLatitude = homeLatitude;
        this.homeLongitude = homeLongitude;
        this.score = score;
    }

    /**
     * getAge returns the age of a Person
     *
     * @return Integer
     */
    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
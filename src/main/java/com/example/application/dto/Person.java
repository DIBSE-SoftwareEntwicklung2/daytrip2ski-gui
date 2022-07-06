package com.example.application.dto;

import java.time.LocalDate;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    private Long id;
    @NotBlank(message="First name is required")
    private String firstName;
    @NotBlank(message="Last name is required")
    private String lastName;
    @NotBlank(message="Email is required")
    @Email(message="Not a valid E-Mail address")
    private String email;
    @NotNull
    private LocalDate dob;
    @Valid
    private Score score;
    private Integer age;
    @NotNull(message="Home position required")
    private Double homeLatitude = 47.269211;
    @NotNull(message="Home position required")
    private Double homeLongitude = 11.404102;

    public Person(String firstName, String lastName, String email, LocalDate dob, Double homeLatitude, double homeLongitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.homeLatitude = homeLatitude;
        this.homeLongitude = homeLongitude;
    }
}
package com.example.application.dto;

import java.time.LocalDate;
import java.time.Period;

import lombok.*;

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

    public Person(String firstName,
                  String lastName,
                  String email,
                  LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
    };

    public Long getId() {
        return id;
    }
    public String getIdStr() {
        return id.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }
}
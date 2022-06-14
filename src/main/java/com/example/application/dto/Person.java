package com.example.application.dto;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private Integer age;

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

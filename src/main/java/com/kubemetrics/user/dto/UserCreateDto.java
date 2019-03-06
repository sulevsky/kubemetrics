package com.kubemetrics.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String email;

    @JsonCreator
    public UserCreateDto() {
    }

    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    @NotBlank
    public String getLastName() {
        return lastName;
    }

    @Email
    public String getEmail() {
        return email;
    }
}
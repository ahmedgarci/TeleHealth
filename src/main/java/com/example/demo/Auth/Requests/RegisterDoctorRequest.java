package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
@Getter
public class RegisterDoctorRequest {
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8,message = "password must be at least 8 characters")
    private String password;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "specialization is required")
    private String specialization;
    @NotNull(message = "experience cant be null")
    private int yearsOfExperience;
    @NotBlank(message = "place is required")
    private String place;
}

package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public  record RegisterPatientRequest(
    @Email(message = "email should be valid")
    String email,
    @NotBlank(message = "password is required")
    @Size(min = 8,message = "password must be at least 8 characters")
    String password,
    @NotBlank(message = "username is required")
    String username
) {
} 
package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticateRequest(
    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    String email,

    @NotNull(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters")
    String password
) {
   
} 


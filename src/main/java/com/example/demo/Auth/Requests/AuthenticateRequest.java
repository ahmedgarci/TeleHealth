package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AuthenticateRequest {
    @Email(message = "email should be valid")
    String email;
    @Size(min=8 , message = "password must be at least 8 characters")
    @NotBlank(message = "password is required")
    String password;
}


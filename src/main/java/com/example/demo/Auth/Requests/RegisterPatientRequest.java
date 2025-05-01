package com.example.demo.Auth.Requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPatientRequest {
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8,message = "password must be at least 8 characters")
    private String password;
    @NotBlank(message = "username is required")
    private String username;

}
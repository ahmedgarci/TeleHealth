package com.example.demo.Auth.Responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse{
    private String Jwt;
    private String username;
    private String role;
    private Integer user_id;
}

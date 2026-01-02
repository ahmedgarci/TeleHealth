package com.example.demo.User.Profile.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String email;
    private String username;
    private String specialization;
    private Integer yearsOfExprerience;
    private String description;
    private Double Longtitude;
    private Double altitude; 
    private byte[] photo;   
}

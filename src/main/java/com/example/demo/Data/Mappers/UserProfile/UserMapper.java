package com.example.demo.Data.Mappers.UserProfile;

import org.springframework.stereotype.Component;

import com.example.demo.Data.Entities.Patient;
import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse FromPatientToUserProfileResponse(Patient patient){  
        return UserResponse.builder()
                                    .email(patient.getEmail())
                                    .username(patient.getName())
                                    .build();
    }

    
}

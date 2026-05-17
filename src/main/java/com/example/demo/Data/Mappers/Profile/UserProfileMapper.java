package com.example.demo.Data.Mappers.Profile;

import org.springframework.stereotype.Component;

import com.example.demo.Data.Entities.User;
import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    public UserResponse toProfileResponse(User user){  
        return UserResponse.builder()
                                    .phoneNumber(null)
                                    .email(user.getEmail())
                                    .username(user.getName())
                                    .build();
    }

    
}

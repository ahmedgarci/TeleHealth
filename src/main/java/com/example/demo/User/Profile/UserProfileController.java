package com.example.demo.User.Profile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.Profile.Responses.UserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("profile")
public class UserProfileController {
    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserDetails(Authentication authentication){
        return ResponseEntity.ok(profileService.getUserInfo(authentication));
    }


}

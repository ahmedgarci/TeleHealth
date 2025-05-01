package com.example.demo.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Auth.Requests.AuthenticateRequest;
import com.example.demo.Auth.Requests.DoctorFinalizeAccountRequest;
import com.example.demo.Auth.Requests.RegisterDoctorRequest;
import com.example.demo.Auth.Requests.RegisterPatientRequest;
import com.example.demo.Auth.Responses.AuthenticationResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerPatient( @Valid @RequestBody RegisterPatientRequest request){
        authService.RegisterPatient(request);
        return ResponseEntity.ok("patient Registred");
    }
    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody RegisterDoctorRequest request){
        authService.RegisterDoctor(request);
        return ResponseEntity.ok("doctor Registred");
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticateRequest request){
        return ResponseEntity.ok(authService.LoginUser(request));
    }

    @PostMapping("/finalize")
    public ResponseEntity<?> finalizeAccount(@RequestPart("file") MultipartFile file,
                    @Valid @RequestPart("request") DoctorFinalizeAccountRequest request ,
                    Authentication authentication
                    ) {
            authService.finalizeAccount(file,authentication,request);
        return ResponseEntity.ok(" account sat");
    }
    




}

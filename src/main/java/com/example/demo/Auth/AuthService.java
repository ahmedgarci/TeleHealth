package com.example.demo.Auth;


import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.demo.Auth.Requests.AuthenticateRequest;
import com.example.demo.Auth.Requests.RegisterPatientRequest;
import com.example.demo.Auth.Responses.AuthenticationResponse;
import com.example.demo.Data.Entities.BaseUser;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Entities.Token;
import com.example.demo.Data.Mappers.Auth.PatientMapper;
import com.example.demo.Data.Repositories.PatientRepo;
import com.example.demo.Data.Repositories.TokenRepo;
import com.example.demo.Security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;
    private final JwtService jwtService;
    private final TokenRepo tokenRepo;

    public void RegisterPatient(RegisterPatientRequest request){
        Patient patient = patientMapper.toPatient(request);
        patientRepo.save(patient);
    }

    // public void RegisterDoctor(RegisterDoctorRequest request){
    //     Doctor doctor = doctorMapper.toDoctor(request);
    //     doctorRepo.save(doctor);
    // }

    public AuthenticationResponse LoginUser(AuthenticateRequest request){
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        BaseUser user = (BaseUser) authentication.getPrincipal();

        System.out.println("user was found  !              aaaaaaaaaaaa");

        final String generatedToken = jwtService.generateToken(user);
    
        RevokeAllUserToken(user);    

        Token t = Token.builder()
                .jwt(generatedToken)
                .build();

        tokenRepo.save(t);
    
        return AuthenticationResponse.builder()
                .Jwt(generatedToken)
                .user_id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .build();
    }
    

    private void RevokeAllUserToken(BaseUser user){
        List<Token> UserValidTokens = tokenRepo.findAllUserValidTokens(user.getId());
        if(UserValidTokens.isEmpty()){
            return;
        }        
        UserValidTokens.forEach((t)->{t.setExpired(true);t.setRevoked(true);});
        tokenRepo.saveAll(UserValidTokens);
    }

}
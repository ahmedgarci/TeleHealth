package com.example.demo.Auth;


import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Auth.Requests.AuthenticateRequest;
import com.example.demo.Auth.Requests.DoctorFinalizeAccountRequest;
import com.example.demo.Auth.Requests.RegisterDoctorRequest;
import com.example.demo.Auth.Requests.RegisterPatientRequest;
import com.example.demo.Auth.Responses.AuthenticationResponse;
import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Entities.Token;
import com.example.demo.Data.Entities.User;
import com.example.demo.Data.Mappers.Auth.DoctorMapper;
import com.example.demo.Data.Mappers.Auth.PatientMapper;
import com.example.demo.Data.Repositories.DoctorRepo;
import com.example.demo.Data.Repositories.PatientRepo;
import com.example.demo.Data.Repositories.TokenRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;
import com.example.demo.Photo.PhotoService;
import com.example.demo.Security.JwtService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final JwtService jwtService;
    private final PhotoService photoService;
    private final TokenRepo tokenRepo;

    public void RegisterPatient(RegisterPatientRequest request){
        Patient patient = patientMapper.toPatient(request);
        patientRepo.save(patient);
    }

    public void RegisterDoctor(RegisterDoctorRequest request){
        Doctor doctor = doctorMapper.toDoctor(request);
        doctorRepo.save(doctor);
    }

    public AuthenticationResponse LoginUser(AuthenticateRequest request){
        
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = (User)authentication.getPrincipal();
        final String generatedToken = jwtService.generateToken(user);
        Token t = Token.builder().jwt(generatedToken).user(user).build();
        RevokeAllUserToken(user);
        tokenRepo.save(t);
        return AuthenticationResponse.builder().Jwt(generatedToken).user_id(user.getId())
                .username(user.getUsername())
                .role(user.getAuthorities().iterator().next().toString())
        .build();
    }
    
    public void finalizeAccount(@NotNull MultipartFile file, 
                Authentication authentication,
                DoctorFinalizeAccountRequest request)
        {
        User conntectedUser = (User)authentication.getPrincipal();
        Doctor doctor = doctorRepo.findById(conntectedUser.getId()).orElseThrow(()->new CustomEntityNotFoundException("doctor dont exist with that id"));
        String PhotoPath = photoService.SaveFile(file, doctor.getId());
        doctor.setPhoto(PhotoPath);
        doctor.setLattitude(request.getLat());
        doctor.setLongtitude(request.getLngt());
        doctor.setDescription(request.getDescription());
        doctorRepo.save(doctor);
    }

    private void RevokeAllUserToken(User user){
        List<Token> UserValidTokens = tokenRepo.findAllUserValidTokens(user.getId());
        if(UserValidTokens.isEmpty()){
            return;
        }        
        UserValidTokens.forEach((t)->{t.setExpired(true);t.setRevoked(true);});
        tokenRepo.saveAll(UserValidTokens);
    }

}
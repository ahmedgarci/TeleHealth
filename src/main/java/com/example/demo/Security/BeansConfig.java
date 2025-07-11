package com.example.demo.Security;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.Data.Entities.Doctor;
import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Repositories.DoctorRepo;
import com.example.demo.Data.Repositories.PatientRepo;
import com.example.demo.GlobalHandler.Exceptions.CustomEntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;

    @Bean
    public UserDetailsService getuserDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
                Optional<Patient> patient = patientRepo.findByEmail(username);
                if(patient.isPresent()){return patient.get();}
                Optional<Doctor> doctor = doctorRepo.findByEmail(username);
                if(doctor.isPresent()){return doctor.get();}
                throw new UsernameNotFoundException("User not found " + username);
    }
    };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(getPasswordEncoder());
        authProvider.setUserDetailsService(getuserDetailsService());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
    throws Exception
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    
}

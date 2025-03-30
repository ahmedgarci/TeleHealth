package com.example.demo.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FilterChain {
    
    private final RequestFilter requestFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
        throws Exception
    {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            return httpSecurity
                    .cors().and()
                    .csrf().disable()
                    .authorizeHttpRequests((request)->
                        request
                            .requestMatchers("/auth/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(requestFilter,UsernamePasswordAuthenticationFilter.class)
                    .build();

    }
}

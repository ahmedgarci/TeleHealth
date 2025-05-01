package com.example.demo.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.demo.Auth.LogoutHandlerImplementation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FilterChain {
    
    private final RequestFilter requestFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;
    private final LogoutHandlerImplementation logoutHandlerImplementation;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
        throws Exception
    {
            return httpSecurity
                    .cors(c->c.configurationSource(corsConfigurationSource))
                    .csrf((c)->c.disable())
                    .authorizeHttpRequests((request)->
                        request
                            .requestMatchers("/auth/register/**","/auth/authenticate","/doctors/**","/ws/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
                    )
                    .sessionManagement((s)->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(requestFilter,UsernamePasswordAuthenticationFilter.class)
                    .logout((logout)-> logout.logoutUrl("/logout")
                                        .addLogoutHandler(logoutHandlerImplementation)             
                                        .logoutSuccessHandler((request, response, authentication) -> {
                                            SecurityContextHolder.clearContext();                    
                                        }))
                    .build();

    }
}

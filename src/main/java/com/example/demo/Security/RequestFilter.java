package com.example.demo.Security;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RequestFilter extends OncePerRequestFilter  {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response,
            FilterChain filterChain)throws ServletException,IOException{
    
        String AuthHeader = request.getHeader("Authorization");
        if(AuthHeader== null || !AuthHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = AuthHeader.substring(7);
        String username = jwtService.extractUsername(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(token,user)){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username
                , user,user.getAuthorities());
                auth.setDetails(user);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);

    }
}
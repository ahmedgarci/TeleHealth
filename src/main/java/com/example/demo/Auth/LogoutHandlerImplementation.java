package com.example.demo.Auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.demo.Data.Entities.Token;
import com.example.demo.Data.Repositories.TokenRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LogoutHandlerImplementation implements LogoutHandler{
    private final TokenRepo tokenRepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            return;
        }
        String jwt = header.substring(7);
        Token token = tokenRepo.findByJwt(jwt).orElse(null);
        if(token == null){return;}
        token.setExpired(true);
        token.setRevoked(true);
        tokenRepo.save(token);
    }
    
}

package com.example.demo.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserDetailsService userDetailsService;    
    @Value("${application.JWT.SECRET_KEY}")
    private String SECRET_KEY;


    public String generateToken(UserDetails userDetails){
        return  generateToken(userDetails,new HashMap<>());
    }
    public String generateToken(UserDetails userDetails,HashMap<String,Object> extraClaims){
        return  Jwts.builder()
                    .addClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis()+3600000))
                    .signWith(generateSignKey(SECRET_KEY))
                    .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(generateSignKey(SECRET_KEY))
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String jwt,UserDetails userDetails){
        return (extractUsername(jwt).equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    public Authentication getAuthentication(String jwt){
        String username = this.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,"", userDetails.getAuthorities());
        return authentication;
    }

    public boolean isTokenExpired(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());    
    }

    private Key generateSignKey(String secretKey){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


}

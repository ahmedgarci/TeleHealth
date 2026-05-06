package com.example.demo.Security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.demo.Data.Entities.Patient;
import com.example.demo.Data.Repositories.PatientRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebsocketInterceptorConfig implements ChannelInterceptor {

        
        private final JwtService jwtService;
        private final PatientRepo patientRepo;
        
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);
            if(accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())){
                String jwt = accessor.getFirstNativeHeader("Authorization");
                if(jwt != null && jwt.startsWith("Bearer ")){
                    String token = jwt.substring(7);
                    String username = jwtService.extractUsername(token);
                    Patient patient = patientRepo.findByEmail(username).orElse(null);
                    if(patient != null && jwtService.isTokenValid(token, patient)){
                        accessor.setUser(new UsernamePasswordAuthenticationToken(patient, null,patient.getAuthorities()));
                    }
                }
            }
		    return message;
	    }
        
    
}

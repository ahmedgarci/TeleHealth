package com.example.demo.Notifications;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final SimpMessagingTemplate messagingTemplate;

    public void SendNotif(Integer userId,Notification notification){
        messagingTemplate.convertAndSendToUser(userId.toString(), "/notification", notification);
    }
    
}

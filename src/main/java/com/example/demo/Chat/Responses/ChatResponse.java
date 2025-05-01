package com.example.demo.Chat.Responses;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {
    private String name;
    private String id;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private Integer sender_Id;
    private Integer receiverId;    
}

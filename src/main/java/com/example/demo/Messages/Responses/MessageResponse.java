package com.example.demo.Messages.Responses;


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
public class MessageResponse {
    
    private Long id;
    private String content;
    private Integer senderId;
    private Integer receiverId;
}

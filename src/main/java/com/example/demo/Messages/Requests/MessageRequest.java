package com.example.demo.Messages.Requests;

import com.example.demo.Data.Enums.MessageType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    @NotBlank(message = "content can t be empty")
    private String content;
    @NotBlank(message = "chatid should not be empty")
    private String chatId;
    @NotNull(message = "sender id is required")
    private Integer senderId;
    @NotNull(message = "receiver id is required")
    private Integer receiverId;
    private MessageType messageType;
    
}
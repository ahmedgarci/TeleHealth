package com.example.demo.Data.Entities;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Data.Constants.MessageConstants;
import com.example.demo.Data.Enums.MessageState;
import com.example.demo.Data.Enums.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
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
@Entity
@NamedQuery(
    name = MessageConstants.FindMessagesByChatId,
    query = "SELECT m FROM Message m WHERE m.chat.id = :ChatId ORDER BY m.created_at DESC"
)
@EntityListeners(AuditingEntityListener.class)
public class Message {
    
    @Id
    @SequenceGenerator(name = "user_seq",sequenceName = "user_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(value = EnumType.STRING)
    private MessageState messageState;

    @Enumerated(value = EnumType.STRING)
    private MessageType Messagetype;
    @Column(nullable = false)
    private Integer senderId;
    @Column(nullable = false)
    private Integer receiverId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @CreatedDate
    private LocalDateTime created_at;

}

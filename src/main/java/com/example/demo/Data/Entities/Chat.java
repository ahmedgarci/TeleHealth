package com.example.demo.Data.Entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Data.Constants.ChatConstants;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@NamedQuery(
    name = ChatConstants.FIND_CHAT_BY_SENDERID_AND_RECEIVERID,
    query = "SELECT DISTINCT c FROM Chat c WHERE c.sender = :senderId AND c.receiver = :receiverId"
)
@NamedQuery(
    name = ChatConstants.FIND_CHAT_BY_USER_ID,
    query = "SELECT DISTINCT c FROM Chat c WHERE c.sender = :userId OR c.receiver = :userId"
)
@EntityListeners(AuditingEntityListener.class)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Integer sender;

    private Integer receiver;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    @OrderBy("created_at ASC")
    private List<Message> messages;


    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(messages.size() - 1).getContent();
        }
        return null;
    }

    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(messages.size() - 1).getCreated_at();
        }
        return null;
    }
}
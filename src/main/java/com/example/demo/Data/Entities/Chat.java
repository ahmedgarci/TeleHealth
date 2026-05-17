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
    query = """
        SELECT DISTINCT c FROM Chat c
        WHERE (c.sender.id = :senderId AND c.receiver.id = :receiverId)
           OR (c.sender.id = :receiverId AND c.receiver.id = :senderId)
        """)
@NamedQuery(
            name = ChatConstants.FIND_CHAT_BY_USER_ID,
            query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :userId OR c.receiver.id = :userId"
)
@EntityListeners(AuditingEntityListener.class)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

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
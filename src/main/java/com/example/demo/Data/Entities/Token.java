package com.example.demo.Data.Entities;


import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Data.Constants.TokenConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(
    name = TokenConstants.FIND_ALL_VALID_TOKENS_BY_USER,
    query = "SELECT t FROM Token t WHERE user.id = :userId And (t.revoked= false OR  t.expired=false )"
)
@EntityListeners(AuditingEntityListener.class)
public class Token {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    
    private String jwt;
    @CreatedDate
    private Date createdAt;
    @Column(columnDefinition = "BOOLEAN")
    private boolean revoked;
    @Column(columnDefinition = "BOOLEAN")
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void initEntity(){
        revoked=false;
        expired=false;
    }

}

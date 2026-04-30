package com.example.demo.Data.Entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Patient extends BaseUser {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Chat> chatsAsSender;
    
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Chat> chatsAsReceiver;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<AppointmentMeet> meets;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Token> tokens;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }
}
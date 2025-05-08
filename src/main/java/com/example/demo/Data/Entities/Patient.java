package com.example.demo.Data.Entities;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;
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
public class Patient extends User {
    private String medicalHistory;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role authorities;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_"+authorities.getRoleName()));
    }
    
    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;
    
    @OneToMany(mappedBy = "receiver")
    private List<Chat> chatsAsReceiver;

    @OneToMany(mappedBy = "patient" )
    private List<AppointmentMeet> meets;



}

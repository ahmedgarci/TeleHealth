package com.example.demo.Data.Entities;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Doctor extends User {
    private String specialization;
    private int yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role authorities;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    
    private Long longtitude;
    
    private Long lattitude;

    private String photo;

    private String description;

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_"+authorities.getRoleName()));
    }
    
    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;

    @OneToMany(mappedBy = "receiver")
    private List<Chat> chatsAsReceiver;
}

package com.example.demo.Data.Entities;


import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Data.Enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class BaseUser implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String email;
    private String username;
    @Column(nullable = false , updatable = false)
    private String password;

    @Column(nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public abstract Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    public String getPassword(){
        return this.password;
    }  
    @Override
	public String getUsername(){
        return this.email;
    }

    public String getName(){
        return this.username;
    }
    
	public boolean isAccountNonExpired(){
        return true;
    }

	public boolean isAccountNonLocked(){
        return true;
    }

	public boolean isCredentialsNonExpired(){
        return true;
    }
    public boolean isEnabled(){
        return true;
    }


}

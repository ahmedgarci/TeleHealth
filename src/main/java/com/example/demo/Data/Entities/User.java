package com.example.demo.Data.Entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class User implements UserDetails{
    @Id
    private String id;
    @Indexed(unique=true)
    private String email;
    private String username;
    private String password;
    private List<Role> authorities;
    public String getPassword(){
        return this.password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities.stream()
        .map((role)->new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
        .collect(Collectors.toList());
    }

	public String getUsername(){
        return this.email;
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

package com.example.eventsmgmt.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.eventsmgmt.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserPrincipal
 */
public class UserPrincipal implements UserDetails {


    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, 
                        String username, 
                        String password, 
                        String email,
                        Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        Set<GrantedAuthority> authorities =  user.getRole().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), authorities);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return authorities;
    }

    @Override
    public String getPassword() {
        
        return password;
    }

    @Override
    public String getUsername() {
        
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    
}
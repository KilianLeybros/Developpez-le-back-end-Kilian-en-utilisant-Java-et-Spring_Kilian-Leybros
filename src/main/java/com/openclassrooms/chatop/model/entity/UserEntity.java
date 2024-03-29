package com.openclassrooms.chatop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;


@Data
@Entity
@Table(name = "users", indexes = @Index(columnList = "email"))
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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



    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public UserEntity setPassword(String password) {
         this.password = password;
        return this;
    }

    public UserEntity setCreatedAt(Timestamp createdAt) {
         this.createdAt = createdAt;
        return this;
    }

    public UserEntity setUpdatedAt(Timestamp updatedAt) {
         this.updatedAt = updatedAt;
        return this;
    }
}

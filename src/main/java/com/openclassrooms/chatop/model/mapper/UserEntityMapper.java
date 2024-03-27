package com.openclassrooms.chatop.model.mapper;

import com.openclassrooms.chatop.model.dto.Profil;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityMapper {

    public static UserEntity fromRegisterInput(RegisterInput registerInput){
        return new UserEntity()
                .setName(registerInput.name())
                .setEmail(registerInput.email())
                .setCreatedAt(Timestamp.from(Instant.now()));
    }

    public static Profil toProfil(UserEntity user){
        return new Profil(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt().toLocalDateTime().toLocalDate(),
                user.getUpdatedAt() != null ?  user.getUpdatedAt().toLocalDateTime().toLocalDate() : null);
    }
}

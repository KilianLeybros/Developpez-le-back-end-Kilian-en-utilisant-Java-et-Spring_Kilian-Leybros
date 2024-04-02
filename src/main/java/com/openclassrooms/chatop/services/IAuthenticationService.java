package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.LoginInput;
import com.openclassrooms.chatop.model.dto.Profil;
import com.openclassrooms.chatop.model.dto.RegisterInput;
import com.openclassrooms.chatop.model.entity.UserEntity;

public interface IAuthenticationService {

    UserEntity register(RegisterInput registerInput);

    UserEntity login(LoginInput loginInput);

    Profil getAuthenticatedUser();

    UserEntity getCurrentUser();

    UserEntity getUserByEmail(String email);
}

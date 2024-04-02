package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.entity.UserEntity;

public interface IUserService {
    UserEntity findUserById(Long id);
}

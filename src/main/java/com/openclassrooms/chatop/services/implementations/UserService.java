package com.openclassrooms.chatop.services.implementations;

import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.services.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final String USER_NOT_FOUND_MESSAGE = "L'utilisateur avec l'id spécifié n'a pas été trouvé";
    @Autowired
    private UserRepository userRepository;
    public UserEntity findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));
    }
}

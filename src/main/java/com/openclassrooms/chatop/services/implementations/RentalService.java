package com.openclassrooms.chatop.services.implementations;

import com.openclassrooms.chatop.model.dto.CreateRentalInput;
import com.openclassrooms.chatop.model.dto.Rental;
import com.openclassrooms.chatop.model.dto.MessageResponse;
import com.openclassrooms.chatop.model.dto.UpdateRentalInput;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.model.mapper.RentalEntityMapper;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.services.IAuthenticationService;
import com.openclassrooms.chatop.services.IRentalService;
import com.openclassrooms.chatop.services.IUploadService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Service
public class RentalService implements IRentalService {

    @Value("${server.port}")
    private String serverPort;

    private final String RENTAL_NOT_FOUND_MESSAGE = "La réservation avec l'id spécifié n'a pas été trouvée";
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private IUploadService uploadService;
    @Autowired
    private IAuthenticationService authenticationService;

    public List<Rental> findRentals(){
       return rentalRepository.findAll().stream().map(RentalEntityMapper::toRental).toList();
    }


    public RentalEntity findRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RENTAL_NOT_FOUND_MESSAGE));
    }


    public MessageResponse createRental(CreateRentalInput rental) throws IOException{
        UserEntity user = authenticationService.getCurrentUser();
        RentalEntity rentalEntity = RentalEntityMapper.fromCreateRentalInput(rental, user);
        if(rental.picture() != null){
            var fileName = uploadService.uploadFile(rental.picture());
            var fileEndpoint = buildFileEndpoint(fileName, InetAddress.getLocalHost());
            rentalEntity.setPicture(fileEndpoint);
        }
        rentalRepository.save(rentalEntity);
        return new MessageResponse("Rental created !");
    }

    public MessageResponse updateRental(Long id, UpdateRentalInput updatedRental) {
        RentalEntity rental = rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RENTAL_NOT_FOUND_MESSAGE));
        RentalEntityMapper.updateRental(updatedRental,rental);

        rentalRepository.save(rental);
        return new MessageResponse("Rental updated !");
    }

    
    private String buildFileEndpoint(String fileName, InetAddress localhost){
        return "http://"+ localhost.getHostAddress() + ":" + serverPort + "/api/rentals/file/" + fileName;
    }
}

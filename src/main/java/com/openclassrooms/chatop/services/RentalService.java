package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.CreateRentalInput;
import com.openclassrooms.chatop.model.dto.Rental;
import com.openclassrooms.chatop.model.dto.MessageResponse;
import com.openclassrooms.chatop.model.dto.UpdateRentalInput;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Value("${server.port}")
    private String serverPort;

    private final String RENTAL_NOT_FOUND_MESSAGE = "La réservation avec l'id spécifié n'a pas été trouvée";
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UploadService uploadService;
    @Autowired
    private AuthenticationService authenticationService;

    public List<Rental> findRentals(){
       return rentalRepository.findAll().stream().map(rental ->
               new Rental(rental.getId(),
                       rental.getName(),
                       rental.getSurface(),
                       rental.getPrice(),
                       rental.getPicture(),
                       rental.getDescription(),
                       rental.getUser().getId(),
                       rental.getCreatedAt().toLocalDateTime().toLocalDate(),
                       Optional.ofNullable(rental.getUpdatedAt()).map(timestamp -> timestamp.toLocalDateTime().toLocalDate()).orElse(null))).toList();

    }


    public RentalEntity findRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RENTAL_NOT_FOUND_MESSAGE));
    }


    public MessageResponse createRental(CreateRentalInput rental) throws IOException{
        UserEntity user = authenticationService.getCurrentUser();
        RentalEntity rentalEntity = new RentalEntity()
                .setName(rental.name())
                .setDescription(rental.description())
                .setSurface(Long.decode(rental.surface()))
                .setUser(user)
                .setPrice(Long.decode(rental.price()))
                .setCreatedAt(Timestamp.from(Instant.now()));
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

        rental.setName(updatedRental.name())
            .setDescription(updatedRental.description())
            .setSurface(Long.decode(updatedRental.surface()))
            .setPrice(Long.decode(updatedRental.price()))
            .setUpdatedAt(Timestamp.from(Instant.now()));

        rentalRepository.save(rental);
        return new MessageResponse("Rental updated !");
    }

    
    private String buildFileEndpoint(String fileName, InetAddress localhost){
        return "http://"+ localhost.getHostAddress() + ":" + serverPort + "/api/rentals/file/" + fileName;
    }
}

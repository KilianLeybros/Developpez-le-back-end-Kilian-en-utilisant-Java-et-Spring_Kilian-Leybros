package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.CreateRentalInput;
import com.openclassrooms.chatop.model.dto.Rental;
import com.openclassrooms.chatop.model.dto.ResponseMessage;
import com.openclassrooms.chatop.model.dto.UpdateRentalInput;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;
import com.openclassrooms.chatop.repository.RentalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class RentalService {

    private final String RENTAL_NOT_FOUND_MESSAGE = "La réservation n'a pas été trouvée";
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Rental> findRentals(){
       return rentalRepository.findAll().stream().map(rental ->
               new Rental(rental.getId(),
                       rental.getName(),
                       rental.getSurface(),
                       rental.getPrice(),
                       rental.getPicture(),
                       rental.getDescription())).toList();

    }


    public Rental findRentalById(Long id) {
        var rental = rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RENTAL_NOT_FOUND_MESSAGE));
        return new Rental(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription());
    }


    public ResponseMessage createRental(CreateRentalInput rental) throws IOException{
        UserEntity user = authenticationService.getCurrentUser();
        RentalEntity rentalEntity = new RentalEntity()
                .setName(rental.name())
                .setDescription(rental.description())
                .setSurface(Long.decode(rental.surface()))
                .setUser(user)
                .setPrice(Long.decode(rental.price()))
                .setCreatedAt(Timestamp.from(Instant.now()));
        if(rental.picture() != null){
            rentalEntity.setPicture(writeRentalPicture(rental.picture()));
        }
        rentalRepository.save(rentalEntity);
        return new ResponseMessage("Rental created !");
    }

    public ResponseMessage updateRental(Long id,UpdateRentalInput updatedRental) {
        RentalEntity rental = rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(RENTAL_NOT_FOUND_MESSAGE));

        rental.setName(updatedRental.name())
            .setDescription(updatedRental.description())
            .setSurface(Long.decode(updatedRental.surface()))
            .setPrice(Long.decode(updatedRental.price()))
            .setUpdatedAt(Timestamp.from(Instant.now()));

        rentalRepository.save(rental);
        return new ResponseMessage("Rental updated !");
    }

    public String writeRentalPicture(MultipartFile file) throws IOException {
        String uploadPath = new ClassPathResource("static").getFile().getPath()+"\\upload\\";
        Path filePath = Paths.get(uploadPath + file.getOriginalFilename());

        File directory = new File(uploadPath);
        File existingFile = new File(filePath.toString());

        if(!directory.exists()){
            new File(uploadPath).mkdirs();
        }
        else if(existingFile.exists()){
            existingFile.delete();
        }

        byte[] fileBytes = file.getBytes();
        Files.write(filePath, fileBytes);

        return filePath.toString();

    }
}

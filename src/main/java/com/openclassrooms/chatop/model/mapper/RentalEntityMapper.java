package com.openclassrooms.chatop.model.mapper;

import com.openclassrooms.chatop.model.dto.CreateRentalInput;
import com.openclassrooms.chatop.model.dto.Rental;
import com.openclassrooms.chatop.model.dto.UpdateRentalInput;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class RentalEntityMapper {
    public static Rental toRental(RentalEntity rental){
        return new Rental(rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getUser().getId(),
                rental.getCreatedAt().toLocalDateTime().toLocalDate(),
                Optional.ofNullable(rental.getUpdatedAt()).map(timestamp -> timestamp.toLocalDateTime().toLocalDate()).orElse(null));
    }

    public static RentalEntity fromCreateRentalInput(CreateRentalInput rental, UserEntity user){
        return new RentalEntity()
                .setName(rental.name())
                .setDescription(rental.description())
                .setSurface(Long.decode(rental.surface()))
                .setUser(user)
                .setPrice(Long.decode(rental.price()))
                .setCreatedAt(Timestamp.from(Instant.now()));
    }

    public static void updateRental(UpdateRentalInput updateRentalInput, RentalEntity rental){
        rental.setName(updateRentalInput.name())
                .setDescription(updateRentalInput.description())
                .setSurface(Long.decode(updateRentalInput.surface()))
                .setPrice(Long.decode(updateRentalInput.price()))
                .setUpdatedAt(Timestamp.from(Instant.now()));
    }
}

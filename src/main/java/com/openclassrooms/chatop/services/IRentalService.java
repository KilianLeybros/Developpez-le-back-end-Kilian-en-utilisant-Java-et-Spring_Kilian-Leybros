package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.model.dto.CreateRentalInput;
import com.openclassrooms.chatop.model.dto.MessageResponse;
import com.openclassrooms.chatop.model.dto.Rental;
import com.openclassrooms.chatop.model.dto.UpdateRentalInput;
import com.openclassrooms.chatop.model.entity.RentalEntity;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public interface IRentalService {
    List<Rental> findRentals();

    RentalEntity findRentalById(Long id);

    MessageResponse createRental(CreateRentalInput rental) throws IOException;

    MessageResponse updateRental(Long id, UpdateRentalInput updatedRental);

}

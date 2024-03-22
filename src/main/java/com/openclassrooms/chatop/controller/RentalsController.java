package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.CreateRentalInput;
import com.openclassrooms.chatop.model.dto.Rental;
import com.openclassrooms.chatop.model.dto.ResponseMessage;
import com.openclassrooms.chatop.model.dto.UpdateRentalInput;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.services.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/rentals")
@RestController
public class RentalsController {


    @Autowired
    private RentalService rentalService;

    @GetMapping
    public List<Rental> getAll(){
        return rentalService.findRentals();
    }

    @GetMapping("/{id}")
    public Rental get(@PathVariable(value = "id") Long id) {
        return rentalService.findRentalById(id);
    }



    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseMessage create(@ModelAttribute @Valid CreateRentalInput model) throws IOException {
        return rentalService.createRental(model);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseMessage update(@PathVariable(value= "id") Long id, @ModelAttribute @Valid UpdateRentalInput model){
        return rentalService.updateRental(id, model);
    }
}

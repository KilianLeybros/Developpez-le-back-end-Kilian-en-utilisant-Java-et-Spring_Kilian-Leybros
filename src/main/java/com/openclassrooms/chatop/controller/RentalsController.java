package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.*;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.model.mapper.RentalEntityMapper;
import com.openclassrooms.chatop.services.IRentalService;
import com.openclassrooms.chatop.services.IUploadService;
import com.openclassrooms.chatop.services.implementations.RentalService;
import com.openclassrooms.chatop.services.implementations.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/rentals")
@Tag(name="Rental API")
@RestController
public class RentalsController {


    @Autowired
    private IRentalService rentalService;

    @Autowired
    private IUploadService uploadService;

    @Operation(summary = "Find Rentals", description = "Permet de lister toutes les locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RentalResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping
    public RentalResponse getAll(){
        List<Rental> rentals = rentalService.findRentals();
        return new RentalResponse(rentals);
    }


    @Operation(summary = "Find Rental by id", description = "Permet de récupérer une location avec son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "404", description = "La location avec l'id spécifié n'a pas été trouvé",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping("/{id}")
    public Rental get(@PathVariable(value = "id") Long id) {
        RentalEntity rentalEntity = rentalService.findRentalById(id);
        return RentalEntityMapper.toRental(rentalEntity);
    }



    @Operation(summary = "Création d'une location", description = "Permet de créer une nouvelle location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location créée avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public MessageResponse create(@ModelAttribute @Valid CreateRentalInput model) throws IOException {
        return rentalService.createRental(model);
    }


    @Operation(summary = "Modification d'une location", description = "Permet de modifier une location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location modifiée avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "La location avec l'id spécifié n'a pas été trouvé",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public MessageResponse update(@PathVariable(value= "id") Long id, @ModelAttribute @Valid UpdateRentalInput model){
        return rentalService.updateRental(id, model);
    }


    @GetMapping(value="/file/{file}")
    public ByteArrayResource getRentalPicture(@PathVariable String file) throws IOException{
        return uploadService.getFileByteByName(file);
    }
}

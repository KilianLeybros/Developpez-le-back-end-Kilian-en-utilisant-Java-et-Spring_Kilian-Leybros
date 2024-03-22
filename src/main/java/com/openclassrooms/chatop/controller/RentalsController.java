package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.dto.*;
import com.openclassrooms.chatop.model.entity.RentalEntity;
import com.openclassrooms.chatop.repository.RentalRepository;
import com.openclassrooms.chatop.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Rental API")
@RestController
public class RentalsController {


    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Find Rentals", description = "Permet de lister toutes les locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Rental.class)))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping
    public List<Rental> getAll(){
        return rentalService.findRentals();
    }


    @Operation(summary = "Find Rental by id", description = "Permet de récupérer une location avec son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "404", description = "La location avec l'id spécifié n'a pas été trouvé",
                    content = {@Content(mediaType = "application/json")}),
    })
    @GetMapping("/{id}")
    public Rental get(@PathVariable(value = "id") Long id) {
        RentalEntity rentalEntity = rentalService.findRentalById(id);
        return new Rental(
                rentalEntity.getId(),
                rentalEntity.getName(),
                rentalEntity.getSurface(),
                rentalEntity.getPrice(),
                rentalEntity.getPicture(),
                rentalEntity.getDescription());
    }



    @Operation(summary = "Création d'une location", description = "Permet de créer une nouvelle location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location créée avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseMessage create(@ModelAttribute @Valid CreateRentalInput model) throws IOException {
        return rentalService.createRental(model);
    }


    @Operation(summary = "Modification d'une location", description = "Permet de modifier une location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location modifiée avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "400", description = "Body de la requête invalide",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "La location avec l'id spécifié n'a pas été trouvé",
                    content = {@Content(mediaType = "application/json")}),
    })
    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseMessage update(@PathVariable(value= "id") Long id, @ModelAttribute @Valid UpdateRentalInput model){
        return rentalService.updateRental(id, model);
    }
}

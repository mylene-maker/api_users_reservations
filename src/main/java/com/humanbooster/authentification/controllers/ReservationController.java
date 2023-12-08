package com.humanbooster.authentification.controllers;

import com.humanbooster.authentification.models.Reservation;
import com.humanbooster.authentification.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;


//*****************    GetAll ! pour avoir la liste de toutes les reservations  ********************//

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "Récupére toutes les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations")
    public List<Reservation> getAll(){

        List<Reservation> reservations = reservationService.findAll();
        return reservations;
    }

    //*****************    Put ! Mettre à jour les informations d'une reservation existant en fonction de son Id  ********************//

    @PutMapping(value = "/{reservation}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Modifie les champs d'une reservation en fonction de son Id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Reservation",
                            summary = "Id de la reservation"
                    )
            }
    ))
    public ResponseEntity<Reservation> updateReservation(@Parameter(description = "Id de la reservation", example = "1") @PathVariable(name = "reservation", required = false) Reservation reservation,
                                                         @Valid @RequestBody Reservation reservationUpdate ){
        if ( reservation == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "reservation inexistante"
            );
        }

        reservationUpdate.setId(reservation.getId());
        reservationUpdate = this.reservationService.saveReservation(reservationUpdate);

        return  new ResponseEntity<Reservation>(reservationUpdate, HttpStatus.OK);

    }

    @PatchMapping(value = "/{reservation}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Modifie les champs d'une reservation en fonction de son Id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Reservation",
                            summary = "Id de la reservation"
                    )
            }
    ))
    public ResponseEntity<Reservation> updateReservation(
            @Parameter(description = "Id de la reservation", example = "1") @PathVariable(name = "reservation", required = false) Reservation reservation,
            @RequestBody Map<String, Object> reservationUpdateFields) {
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation inexistante");
        }

        // Mettez à jour uniquement les champs spécifiés dans la requête PATCH
        if (reservationUpdateFields.containsKey("accepted")) {
            reservation.setAccepted((Boolean) reservationUpdateFields.get("accepted"));
        }
        if (reservationUpdateFields.containsKey("column")) {
            Integer columnValue = (Integer) reservationUpdateFields.get("column");

            reservation.setColumn(columnValue);

        }

        // Ajoutez d'autres champs selon vos besoins

        // Enregistrez la mise à jour
        reservation = this.reservationService.saveReservation(reservation);

        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }



    //*****************    Delete ! supprimer une reservation en fonction de son Id  ********************//


    @DeleteMapping(value = "/{reservation}")
    @Operation(summary = "Supprime une reservation en fonction de son Id") // annotation swagger qui permet de documenter le titre de l'api
    @ApiResponses(value = { // annotation swagger pour indiquer les différents status possibles lors de la soumission de la requette
            @ApiResponse(description = "Cette reservation n'existe pas", responseCode = "404"),
            @ApiResponse(description = "Impossible de supprimer cette reservation", responseCode = "400"),
            @ApiResponse(description = "Reservation supprime !", responseCode = "200")
    })
    void deleteReservation(@Parameter(description = "Id de la reservation", example = "1") @PathVariable(name = "reservation", required = false) Reservation reservation){
        if ( reservation == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reservation introuvable"
            );
        }else {
            if (reservation.isAccepted()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Impossible de supprimer cette reservation, elle a deja ete validées");
            }

            this.reservationService.removeReservation(reservation);
        }

    }
}

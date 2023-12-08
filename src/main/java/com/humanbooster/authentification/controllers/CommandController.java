package com.humanbooster.authentification.controllers;

import com.humanbooster.authentification.models.Command;
import com.humanbooster.authentification.services.CommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/commands")
@RestController
public class CommandController {

    @Autowired
    CommandService commandService;

    //*****************    GetAll ! pour avoir la liste de toutes les commandes  ********************//

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "Récupére toutes les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations")
    public List<Command> getAll(){

        List<Command> commands = commandService.findAll();
        return commands;
    }

}


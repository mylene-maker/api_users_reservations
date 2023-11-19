package com.humanbooster.authentification.controllers;



import com.humanbooster.authentification.models.User;
import com.humanbooster.authentification.services.CommandService;
import com.humanbooster.authentification.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CommandService commandService;

//*****************    GetAll ! pour avoir la liste de tout les utilisateurs  ********************//

    @GetMapping(value = "/", produces = "application/json")
    @Operation(summary = "Récupére tous les utiisateurs")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs")
    public List<User> getAll(){

        List<User> users = userService.findAll();
        return users;
    }

//*****************    GetOne ! pour avoir un utilisateur en fonction de son Id ********************//

    @GetMapping(value = "/{user}", produces = "application/json")
    @Operation(summary = "Récupére un utiisateur en fonction de son Id")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "404", description = "Cet utilisateur n'existe pas",
            content = @Content)
    })
    public User getOne(@Parameter(description = "Id de l'utilisateur", example = "1") @PathVariable(name = "user", required = false) User user){
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur introuvale");
        }
        return user ;
    }


//*****************    Post ! pour creer et enregistrer un utilisateur  ********************//

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Enregistrer un utilisateur")
    @ApiResponses(value = { // annotation swagger pour indiquer les différents status possibles lors de la soumission de la requette
            @ApiResponse(description = "Utilisateur creer", responseCode = "201"),
            @ApiResponse(description = "Un utilisateur avec cet email existe déjà !", responseCode = "400"),
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Utilisateur",
                            summary = "Utilisateur sans Id et sans commandes",
                            value =
                                    "{\n" +
                                            "  \"lastname\": \"TOTO\",\n" +
                                            "  \"firstname\": \"Toto\",\n" +
                                            "  \"email\": \"hello@mail.fr\",\n" +
                                            "  \"country\": \"FRANCE\",\n" +
                                            "  \"password\": \"123\",\n" +
                                            "  \"roles\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 1,\n" +
                                            "      \"roleName\": \"USER\"\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                    )
            }
    ))
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
        User existUser = this.userService.findByEmail(user.getEmail());
        if (existUser!= null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Un utilisateur avec cet email existe déjà");
        }
        user = this.userService.saveUser(user);
        return  new ResponseEntity<User>(user, HttpStatus.CREATED);
    }


//*****************    Put ! Mettre à jour les informations d'un utilisateur existant en fonction de son Id  ********************//

    @PutMapping(value = "/{user}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Modifie les champs d'un utiisateur en fonction de son Id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Utilisateur",
                            summary = "Id de l'utilisateur",
                            value =
                                    "{\n" +
                                            "  \"lastname\": \"TOTO\",\n" +
                                            "  \"firstname\": \"Toto\",\n" +
                                            "  \"email\": \"hello@mail.fr\",\n" +
                                            "  \"country\": \"FRANCE\",\n" +
                                            "  \"password\": \"123\",\n" +
                                            "  \"roles\": [\n" +
                                            "    {\n" +
                                            "      \"id\": 1,\n" +
                                            "      \"roleName\": \"USER\"\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                    )
            }
    ))
    public ResponseEntity<User> updateUser(@Parameter(description = "Id de l'utilisateur", example = "1") @PathVariable(name = "user", required = false) User user,
                                           @Valid @RequestBody User userUpdate ){
       if ( user == null){
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND, "Utilisateur inexistant"
           );
       }

        userUpdate.setId(user.getId());
        userUpdate = this.userService.saveUser(userUpdate);

        return  new ResponseEntity<User>(userUpdate, HttpStatus.OK);

    }


//*****************    Delete ! supprimer un utilisateur en fonction de son Id  ********************//


    @DeleteMapping(value = "/{user}")
    @Operation(summary = "Supprime un utiisateur en fonction de son Id") // annotation swagger qui permet de documenter le titre de l'api
    @ApiResponses(value = { // annotation swagger pour indiquer les différents status possibles lors de la soumission de la requette
            @ApiResponse(description = "Cet utilisateur n'existe pas", responseCode = "404"),
            @ApiResponse(description = "Cet utilisateur a des commande, suppression impossible", responseCode = "400"),
            @ApiResponse(description = "Utilisateur supprime !", responseCode = "200")
    })
    void deleteUser(@Parameter(description = "Id de l'utilisateur", example = "1") @PathVariable(name = "user", required = false) User user){
        if ( user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Utilisateur introuvable"
            );
        }else {
            if (!user.getCommands().isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Impossible de supprimer cet utilisateur, il a des commandes");
            }

            this.userService.removeUser(user);
        }

    }
}


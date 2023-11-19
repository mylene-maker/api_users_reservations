package com.humanbooster.authentification.controllers;

import com.humanbooster.authentification.config.JwtTokenUtils;
import com.humanbooster.authentification.models.User;
import com.humanbooster.authentification.models.requests.AuthRequest;
import com.humanbooster.authentification.models.responses.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @PostMapping(value = "/auth")
    @Operation(summary = "Obtenir un token pour se connecter")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(
            examples = {
                    @ExampleObject(
                            name = "Utilisateur",
                            summary = "Utilisateur",
                            value =
                                    "{\n" +
                                            "  \"email\": \"admin@admin.com\",\n" +
                                            "  \"password\": \"admin\" " +
                                            "}"
                    )
            }
    ))
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Identifiants erronnés",
                    content = @Content)
    })
    public ResponseEntity<JwtResponse> authentication(@RequestBody AuthRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping(value = "/me")
    @Operation(summary = "Obtenir l'utilisateur connectée")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "401", description = "Ressource non autorisé",
                    content = @Content)
    })
    public UserDetails getCurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails;
    }
}


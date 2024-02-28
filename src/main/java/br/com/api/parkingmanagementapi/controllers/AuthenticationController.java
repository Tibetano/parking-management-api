package br.com.api.parkingmanagementapi.controllers;

import br.com.api.parkingmanagementapi.dtos.authentication.AuthenticationDTO;
import br.com.api.parkingmanagementapi.dtos.authentication.LoginResponseDTO;
import br.com.api.parkingmanagementapi.models.User;
import br.com.api.parkingmanagementapi.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.username(),authenticationDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}

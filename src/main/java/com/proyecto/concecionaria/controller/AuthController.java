package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Auth.AuthLoginRequestDTO;
import com.proyecto.concecionaria.DTOs.Auth.AuthResponseDTO;
import com.proyecto.concecionaria.service.AuthService;
import com.proyecto.concecionaria.service.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación JWT")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario y devuelve un token JWT para acceder a los endpoints protegidos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token JWT generado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Parameter(description = "Credenciales del usuario (email y password)", required = true)
            @RequestBody @Valid AuthLoginRequestDTO userRequest) {
        return new ResponseEntity<>(this.authService.loginUser(userRequest), HttpStatus.OK);
    }
}
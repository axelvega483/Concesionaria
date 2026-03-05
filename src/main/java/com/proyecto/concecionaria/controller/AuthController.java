package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Auth.AuthLoginRequestDTO;
import com.proyecto.concecionaria.DTOs.Auth.AuthResponseDTO;
import com.proyecto.concecionaria.service.AuthService;
import com.proyecto.concecionaria.service.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")  // ← Este path debe estar en .permitAll() de SecurityConfig
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación JWT")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody @Valid AuthLoginRequestDTO userRequest) {
       return new ResponseEntity<>(this.authService.loginUser(userRequest), HttpStatus.OK);
    }

}

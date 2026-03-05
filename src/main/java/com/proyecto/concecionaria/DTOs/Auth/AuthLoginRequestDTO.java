package com.proyecto.concecionaria.DTOs.Auth;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(@NotBlank String username, @NotBlank String password) {

}

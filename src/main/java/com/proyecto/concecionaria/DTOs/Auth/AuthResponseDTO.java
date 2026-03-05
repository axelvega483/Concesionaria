package com.proyecto.concecionaria.DTOs.Auth;

public record AuthResponseDTO(String username,
                              String mensaje,
                              String token,
                              boolean status) {
}

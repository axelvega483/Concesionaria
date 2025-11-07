package com.proyecto.concecionaria.DTOs.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientePostDTO {

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras")
    @NotNull(message = "El nombre no puede estar vacio")
    private String nombre;

    @Email(message = "El email debe ser válido")
    @NotNull(message = "El email no puede estar vacío")
    private String email;

    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener entre 7 y 8 dígitos")
    @NotNull(message = "El dni no puede estar vacío")
    private String dni;
}

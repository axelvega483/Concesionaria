package com.proyecto.concecionaria.DTOs.Venta;

import jakarta.validation.constraints.NotNull;

public record VentaUsuarioID(
        @NotNull
        Integer id) {

}

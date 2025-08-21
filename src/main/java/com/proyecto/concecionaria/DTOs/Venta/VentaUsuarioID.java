package com.proyecto.concecionaria.DTOs.Venta;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaUsuarioID {
    @NotNull
    private Integer id;
}

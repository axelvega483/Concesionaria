package com.proyecto.concecionaria.DTOs.DetallesVenta;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaPostDTO {

    @NotNull
    private Integer VehiculoId;
    @NotNull
    private Integer cantidad;
    @NotNull
    private BigDecimal precioUnitario;

}

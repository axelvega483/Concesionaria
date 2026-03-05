package com.proyecto.concecionaria.DTOs.DetallesVenta;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DetalleVentaPostDTO(@NotNull
                                  Integer vehiculoId,
                                  @NotNull
                                  Integer cantidad,
                                  @NotNull
                                  BigDecimal precioUnitario) {
}

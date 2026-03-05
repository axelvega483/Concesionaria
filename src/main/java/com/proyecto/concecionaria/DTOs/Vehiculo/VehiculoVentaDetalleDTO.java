package com.proyecto.concecionaria.DTOs.Vehiculo;

import java.math.BigDecimal;

public record VehiculoVentaDetalleDTO(
        Integer id,
         Integer cantidad,
         BigDecimal precioUnitario) {



}

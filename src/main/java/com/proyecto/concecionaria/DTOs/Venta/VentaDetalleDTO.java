package com.proyecto.concecionaria.DTOs.Venta;

import java.math.BigDecimal;

public record VentaDetalleDTO(
         Integer id,
         Integer cantidad,
         BigDecimal precioUnitario) {


}

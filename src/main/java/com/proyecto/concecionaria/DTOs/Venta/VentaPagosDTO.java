package com.proyecto.concecionaria.DTOs.Venta;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.concecionaria.util.EstadoPagos;

public record VentaPagosDTO(
         Integer id,
         LocalDate fechaPago,
         BigDecimal monto,
         EstadoPagos estado) {


}

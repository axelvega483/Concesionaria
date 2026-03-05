package com.proyecto.concecionaria.DTOs.Pagos;

import com.proyecto.concecionaria.util.EstadoPagos;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.concecionaria.util.MetodoPago;

public record PagosGetDTO(Integer id,
         LocalDate fechaPago,
         MetodoPago metodoPago,
         BigDecimal monto,
         PagoVenta venta,
         EstadoPagos estado,
         boolean activo) {

}

package com.proyecto.concecionaria.DTOs.DetallesVenta;

import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.entity.Venta;

import java.math.BigDecimal;


public record DetalleGetDTO(Integer id,
                            Vehiculo vehiculo,
                            Integer cantidad,
                            BigDecimal precioUnitario,
                            Venta venta) {
}

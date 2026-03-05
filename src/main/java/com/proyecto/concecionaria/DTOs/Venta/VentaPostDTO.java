package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VentaPostDTO(
        @NotNull(message = "La fecha de la venta no puede estar vacía")
        @Column(nullable = false)
        LocalDate fecha,
        @NotNull(message = "El total no puede estar vacío")
        @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
        BigDecimal total,
        @NotNull(message = "La frecuencia de pago no puede estar vacía")
        FrecuenciaPago frecuenciaPago,
        @NotNull(message = "La venta debe estar asociada a un cliente")
        Integer clienteId,
        @NotNull(message = "La venta debe ser registrada por un empleado")
        Integer empleadoId,
        @NotNull
        Double entrega,
        @NotNull
        EstadoVenta estado,
        @NotNull
        Integer cuotas,
        @NotNull(message = "Debe incluir al menos un detalle de venta")
        @Size(min = 1, message = "Debe haber al menos un producto en la venta")
        List<DetalleVentaPostDTO> detalleVentas) {

}

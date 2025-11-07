package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaPostDTO {
    @NotNull(message = "La fecha de la venta no puede estar vacía")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull(message = "El total no puede estar vacío")
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    private BigDecimal total;

    @NotNull(message = "La frecuencia de pago no puede estar vacía")
    private FrecuenciaPago frecuenciaPago;
    @NotNull(message = "La venta debe estar asociada a un cliente")
    private Integer clienteId;
    @NotNull(message = "La venta debe ser registrada por un empleado")
    private Integer empleadoId;
    @NotNull
    private Double entrega;
    @NotNull
    private EstadoVenta estado;
    @NotNull
    private Integer cuotas;
    @NotNull(message = "Debe incluir al menos un detalle de venta")
    @Size(min = 1, message = "Debe haber al menos un producto en la venta")
    private List<DetalleVentaPostDTO> detalleVentas;
}

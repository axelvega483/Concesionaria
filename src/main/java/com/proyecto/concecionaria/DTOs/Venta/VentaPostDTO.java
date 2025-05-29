package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaPostDTO {

    @NotNull
    private Integer id;
    @NotNull
    private LocalDateTime fecha;
    @NotNull
    private BigDecimal total;
    @NotNull
    private FrecuenciaPago frecuenciaPago;
    @NotNull
    private Cliente cliente;
    @NotNull
    private Usuario empleado;
    @NotNull
    private Boolean activo;
    @NotNull
    private Double entrega;
    @NotNull
    private EstadoVenta estado;
    @NotNull
    private Integer cuotas;
    @NotNull
    private List<DetalleVentaPostDTO> detalleVentas;
}

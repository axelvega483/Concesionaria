package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaPostDTO {

    @NotNull
    private BigDecimal total;
    @NotNull
    private FrecuenciaPago frecuenciaPago;
    @NotNull
    private VentaClienteID clienteId;
    @NotNull
    private VentaUsuarioID empleadoId;
    @NotNull
    private Double entrega;
    @NotNull
    private EstadoVenta estado;
    @NotNull
    private Integer cuotas;
    @NotNull
    private List<DetalleVentaPostDTO> detalleVentas;
}

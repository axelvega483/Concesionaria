package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaGetDTO {

    private Integer id;
    private LocalDateTime fecha;
    private FrecuenciaPago frecuenciaPago;
    private BigDecimal total;
    private List<VentaDetalleDTO> detalleVentas;
    private Cliente cliente;
    private Usuario empleado;
    private List<VentaPagosDTO> pagos;
    private Boolean activo;
    private Double entrega;
    private EstadoVenta estado;
    private Integer cuotas;
}

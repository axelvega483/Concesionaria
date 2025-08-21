package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
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
public class VentaPutDTO {

    private BigDecimal total;

    private FrecuenciaPago frecuenciaPago;

    private List<DetalleVentaPostDTO> detalleVentas;

    private VentaClienteID clienteID;

    private VentaUsuarioID empleadoID;

    private List<Integer> pagosId;

    private Boolean activo;

    private Double entrega;

    private EstadoVenta estado;

    private Integer cuotas;
}

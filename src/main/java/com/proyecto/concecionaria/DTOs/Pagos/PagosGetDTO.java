package com.proyecto.concecionaria.DTOs.Pagos;

import com.proyecto.concecionaria.util.EstadoPagos;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.proyecto.concecionaria.util.MetodoPago;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagosGetDTO {

    private Integer id;
    private LocalDate fechaPago;
    private MetodoPago metodoPago;
    private BigDecimal monto;
    private PagoVenta venta;
    private EstadoPagos estado;
    private Boolean activo;

}

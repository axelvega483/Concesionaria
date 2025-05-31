package com.proyecto.concecionaria.DTOs.Pagos;

import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.util.EstadoPagos;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagosGetDTO {

    private Integer id;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private BigDecimal monto;
    private Venta venta;
    private EstadoPagos estado;
    private Boolean activo;

}

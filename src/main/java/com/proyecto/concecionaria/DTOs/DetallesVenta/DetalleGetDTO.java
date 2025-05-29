package com.proyecto.concecionaria.DTOs.DetallesVenta;

import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.entity.Venta;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleGetDTO {

    private Integer id;
    private Vehiculo vehiculo;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private Venta venta;
}

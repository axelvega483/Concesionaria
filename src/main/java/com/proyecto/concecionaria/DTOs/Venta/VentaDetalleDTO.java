package com.proyecto.concecionaria.DTOs.Venta;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetalleDTO {

    private Integer id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}

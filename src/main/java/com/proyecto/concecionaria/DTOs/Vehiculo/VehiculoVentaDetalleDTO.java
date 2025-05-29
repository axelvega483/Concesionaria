package com.proyecto.concecionaria.DTOs.Vehiculo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoVentaDetalleDTO {

    private Integer id;
    private Integer cantidad;
    private BigDecimal precioUnitario;

}

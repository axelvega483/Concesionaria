package com.proyecto.concecionaria.DTOs.Venta;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaPagosDTO {

    private Integer id;
    private LocalDate fechaPago;
    private BigDecimal monto;

}

package com.proyecto.concecionaria.DTOs.Pagos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagoVenta {
    private Integer id;
    private BigDecimal total;
}

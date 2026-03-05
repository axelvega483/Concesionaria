package com.proyecto.concecionaria.DTOs.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClienteVentaDTO( Integer id,
         LocalDate fecha,
         BigDecimal total) {

}

package com.proyecto.concecionaria.DTOs.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UsuarioVentaDTO(Integer id,
                              LocalDate fecha,
                              BigDecimal total) {
}

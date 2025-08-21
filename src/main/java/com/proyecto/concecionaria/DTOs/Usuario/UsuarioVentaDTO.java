package com.proyecto.concecionaria.DTOs.Usuario;

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
public class UsuarioVentaDTO {

    private Integer id;
    private LocalDate fecha;
    private BigDecimal total;

}

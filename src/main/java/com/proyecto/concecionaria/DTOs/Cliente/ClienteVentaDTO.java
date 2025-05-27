package com.proyecto.concecionaria.DTOs.Cliente;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteVentaDTO {

    private Integer id;
    private LocalDateTime fecha;
    private Double total;
}

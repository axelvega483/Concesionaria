package com.proyecto.concecionaria.DTOs.Pagos;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagosPutDTO {

    private LocalDateTime fechaPago;
    private String metodoPago;
}

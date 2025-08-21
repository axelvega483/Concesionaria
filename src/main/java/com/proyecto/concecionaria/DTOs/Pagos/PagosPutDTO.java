package com.proyecto.concecionaria.DTOs.Pagos;


import com.proyecto.concecionaria.util.MetodoPago;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagosPutDTO {

    private MetodoPago metodoPago;
}

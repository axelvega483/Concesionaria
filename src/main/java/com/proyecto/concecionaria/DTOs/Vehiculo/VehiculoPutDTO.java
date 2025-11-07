package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.util.EstadoVehiculo;
import com.proyecto.concecionaria.util.TipoVehiculo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoPutDTO {

    private String marca;
    private String modelo;
    private Integer anioModelo;
    private Double precio;
    private Integer stock;
    private String color;
    private TipoVehiculo tipo;
    private EstadoVehiculo estado;
    private Integer kilometraje;
}

package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.util.EstadoVehiculo;
import com.proyecto.concecionaria.util.TipoVehiculo;

public record VehiculoPutDTO(
         String marca,
         String modelo,
         Integer anioModelo,
         Double precio,
         Integer stock,
         String color,
         TipoVehiculo tipo,
         EstadoVehiculo estado,
         Integer kilometraje) {

}

package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.util.EstadoVehiculo;
import com.proyecto.concecionaria.util.TipoVehiculo;
import java.util.List;

public record VehiculoGetDTO(Integer id,
         String marca,
         String modelo,
         Integer anioModelo,
         Double precio,
         Integer stock,
         String color,
         TipoVehiculo tipo,
         EstadoVehiculo estado,
         Integer kilometraje,
         List<String> imagenes,
         Boolean activo,
         List<VehiculoVentaDetalleDTO> detalleVentas) {

}

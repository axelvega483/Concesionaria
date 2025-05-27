package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.util.EstadoVehiculo;
import com.proyecto.concecionaria.util.TipoVehiculo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoGetDTO {

    private Integer id;
    private String marca;
    private String modelo;
    private Integer anioModelo;
    private Double precio;
    private Integer stock;
    private TipoVehiculo tipo;
    private EstadoVehiculo estado;
    private Integer kilometraje;
    private List<String> imagenes;
    private Boolean activo;
    private List<VehiculoVentaDetalleDTO> detalleVentas;

}

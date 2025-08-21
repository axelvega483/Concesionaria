package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.util.EstadoVehiculo;
import com.proyecto.concecionaria.util.TipoVehiculo;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculoPostDTO {
    @NotNull
    private String marca;
    @NotNull
    private String modelo;
    @NotNull
    private Integer anioModelo;
    @NotNull
    private Double precio;
    @NotNull
    private Integer stock;
    @NotNull
    private String color;
    @NotNull
    private TipoVehiculo tipo;
    @NotNull
    private EstadoVehiculo estado;
    @NotNull
    private Integer kilometraje;
    @NotNull
    private List<String> nombresImagenes;
    @NotNull
    private Boolean activo;
    @NotNull
    private List<Integer> detalleVentasId;

}

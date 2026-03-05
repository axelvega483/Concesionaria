package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.util.EstadoVehiculo;
import com.proyecto.concecionaria.util.TipoVehiculo;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public record VehiculoPostDTO( @NotNull
                                String marca,
                                       @NotNull
                                        String modelo,
                                       @NotNull
                                        Integer anioModelo,
                                       @NotNull
                                        Double precio,
                                       @NotNull
                                        Integer stock,
                                       @NotNull
                                        String color,
                                       @NotNull
                                        TipoVehiculo tipo,
                                       @NotNull
                                        EstadoVehiculo estado,
                                       @NotNull
                                        Integer kilometraje,
                                       @NotNull
                                        List<String> nombresImagenes) {

}

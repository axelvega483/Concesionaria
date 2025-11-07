package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoGetDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPostDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPutDTO;
import com.proyecto.concecionaria.entity.Vehiculo;

import java.util.List;
import java.util.Optional;

public interface VehiculoInterfaz {
    VehiculoGetDTO guardar(Vehiculo vehiculo);

    VehiculoGetDTO crear(VehiculoPostDTO post);

    VehiculoGetDTO actualizar(Integer id, VehiculoPutDTO put);

    Optional<VehiculoGetDTO> obtener(Integer id);

    Optional<Vehiculo> obtenerEntidad(Integer id);


    List<VehiculoGetDTO> listar();

    VehiculoGetDTO eliminar(Integer id);
}

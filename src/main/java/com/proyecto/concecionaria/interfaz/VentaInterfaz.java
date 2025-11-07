package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.DTOs.Venta.VentaGetDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaPostDTO;

import java.util.List;
import java.util.Optional;

public interface VentaInterfaz {

    VentaGetDTO crear(VentaPostDTO post);

    Optional<VentaGetDTO> obtener(Integer id);

    List<VentaGetDTO> listar();

    VentaGetDTO cancelar(Integer id);

}

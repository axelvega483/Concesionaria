package com.proyecto.concecionaria.DTOs.Cliente;

import java.util.List;


public record ClienteGetDTO(Integer id,
         String nombre,
         String email,
         String dni,
         Boolean activo,
         List<ClienteVentaDTO> ventas) {

}

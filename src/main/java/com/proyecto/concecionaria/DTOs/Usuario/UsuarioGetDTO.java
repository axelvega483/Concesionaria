package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolEmpleado;

import java.util.List;

public record UsuarioGetDTO(Integer id,
                            String nombre,
                            String email,
                            String dni,
                            RolEmpleado rol,
                            boolean activo,
                            List<UsuarioVentaDTO> ventas) {


}

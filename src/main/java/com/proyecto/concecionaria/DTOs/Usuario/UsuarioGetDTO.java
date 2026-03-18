package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolUsuario;

import java.util.List;

public record UsuarioGetDTO(Integer id,
                            String nombre,
                            String email,
                            String dni,
                            RolUsuario rol,
                            boolean activo,
                            List<UsuarioVentaDTO> ventas) {


}

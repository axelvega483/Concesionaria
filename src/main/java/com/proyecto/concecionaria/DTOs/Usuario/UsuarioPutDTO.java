package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolUsuario;

import java.util.List;

public record UsuarioPutDTO(String nombre,
                            String email,
                            String dni,
                            String password,
                            RolUsuario rol,
                            List<Integer> ventasId) {
}
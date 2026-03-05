package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolEmpleado;

import java.util.List;

public record UsuarioPutDTO(String nombre,
                            String email,
                            String dni,
                            String password,
                            RolEmpleado rol,
                            List<Integer> ventasId) {
}
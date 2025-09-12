package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolEmpleado;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioPostDTO {

    @NotNull
    private String nombre;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String dni;

    @NotNull
    private RolEmpleado rol;

    @NotNull
    private Boolean activo;

    private List<Integer> ventasId;

}

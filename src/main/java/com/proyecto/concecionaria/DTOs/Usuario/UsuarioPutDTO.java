package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolEmpleado;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UsuarioPutDTO {

    private Integer id;

    private String nombre;

    private String email;

    private String dni;
    
    private String password;

    private RolEmpleado rol;

    private Boolean activo;

    private List<Integer> ventasId;

    private List<Integer> comprasId;
}

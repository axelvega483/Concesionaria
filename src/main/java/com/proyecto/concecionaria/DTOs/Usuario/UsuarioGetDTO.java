package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.util.RolEmpleado;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioGetDTO {

    private Integer id;

    private String nombre;

    private String email;

    private String dni;

    private RolEmpleado rol;

    private boolean activo;

    private List<UsuarioVentaDTO> ventas;

}

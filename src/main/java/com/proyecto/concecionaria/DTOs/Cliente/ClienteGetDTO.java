package com.proyecto.concecionaria.DTOs.Cliente;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteGetDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String dni;
    private Boolean activo;
    private List<ClienteVentaDTO> ventas;

}

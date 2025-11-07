package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.DTOs.Cliente.ClienteGetDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePostDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePutDTO;

import java.util.List;
import java.util.Optional;

public interface ClienteInterfaz {

    ClienteGetDTO crear(ClientePostDTO post);

    Optional<ClienteGetDTO> obtener(Integer id);

    List<ClienteGetDTO> listar();

    ClienteGetDTO actualizar(Integer id, ClientePutDTO put);

    ClienteGetDTO eliminar(Integer id);
}

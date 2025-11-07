package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPostDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPutDTO;
import com.proyecto.concecionaria.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioInterfaz {

    Usuario guardar(Usuario usuario);

    UsuarioGetDTO crear(UsuarioPostDTO post);

    Optional<UsuarioGetDTO> obtener(Integer id);

    List<UsuarioGetDTO> listar();

    UsuarioGetDTO actualizar(Integer id, UsuarioPutDTO put);

    UsuarioGetDTO eliminar(Integer id);

    Optional<UsuarioGetDTO> findByCorreoAndPassword(String email, String password);

    Boolean existe(String dni);
}

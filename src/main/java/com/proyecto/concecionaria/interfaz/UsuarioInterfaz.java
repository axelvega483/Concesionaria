package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioInterfaz {

    public Usuario guardar(Usuario usuario);

    public Optional<Usuario> obtener(Integer id);

    public List<Usuario> listar();

    public void eliminar(Integer id);

    public Optional<Usuario> findByCorreoAndPasswoed(String email, String password);

    public Boolean existe(String dni);
}

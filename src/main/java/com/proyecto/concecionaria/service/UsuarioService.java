package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.interfaz.UsuarioInterfaz;
import com.proyecto.concecionaria.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioInterfaz {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public Optional<Usuario> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Usuario> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(usuario -> {
            usuario.setActivo(Boolean.FALSE);
            repo.save(usuario);
        });
    }

    public Boolean existe(String dni) {
        return repo.findByDniAndActivo(dni).isPresent();
    }

    public Optional<Usuario> findByCorreoAndPasswoed(String email, String password) {
        return repo.findByCorreoAndPassword(email, password);
    }

}

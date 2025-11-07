package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioMapper;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPostDTO;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.interfaz.UsuarioInterfaz;
import com.proyecto.concecionaria.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import com.proyecto.concecionaria.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioInterfaz {

    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private UsuarioMapper mapper;
    @Autowired
    private VentaRepository ventaRepo;

    @Override
    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    public UsuarioGetDTO crear(UsuarioPostDTO post) {
        if (existe(post.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        Usuario usuario = mapper.toEntity(post);
        repo.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    public Optional<UsuarioGetDTO> obtener(Integer id) {
        return repo.findById(id).filter(Usuario::isActivo).map(mapper::toDTO);
    }

    @Override
    public List<UsuarioGetDTO> listar() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public UsuarioGetDTO eliminar(Integer id) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuario.setActivo(false);
        repo.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    public UsuarioGetDTO actualizar(Integer id, com.proyecto.concecionaria.DTOs.Usuario.UsuarioPutDTO put) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<Venta> ventas = ventaRepo.findAllById((put.getVentasId()));
        if (ventas.size() != put.getVentasId().size()) {
            throw new IllegalArgumentException("Una o más ventas no fueron encontradas");
        }
        mapper.fromUpdateDTO(put, usuario, ventas);
        usuario.setActivo(true);
        repo.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    public Boolean existe(String dni) {
        return repo.findByDniAndActivo(dni).isPresent();
    }

    @Override
    public Optional<UsuarioGetDTO> findByCorreoAndPassword(String email, String password) {
        return repo.findByCorreoAndPassword(email, password).map(mapper::toDTO);
    }

}

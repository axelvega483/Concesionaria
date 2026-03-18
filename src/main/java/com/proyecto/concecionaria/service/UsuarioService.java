package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.DTOs.Usuario.UsuarioGetDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioMapper;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioPostDTO;
import com.proyecto.concecionaria.DTOs.Usuario.UsuarioRolDTO;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.interfaz.UsuarioInterfaz;
import com.proyecto.concecionaria.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import com.proyecto.concecionaria.repository.VentaRepository;
import com.proyecto.concecionaria.util.RolUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioInterfaz {

    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private UsuarioMapper mapper;
    @Autowired
    private VentaRepository ventaRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }

    @Override
    public UsuarioGetDTO crear(UsuarioPostDTO post) {
        if (existe(post.dni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }
        Usuario usuario = mapper.toEntity(post);
        usuario.setPassword(passwordEncoder.encode(post.password()));
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

        List<Venta> ventas = ventaRepo.findAllById((put.ventasId()));
        if (ventas.size() != put.ventasId().size()) {
            throw new IllegalArgumentException("Una o más ventas no fueron encontradas");
        }
        mapper.fromUpdateDTO(put, usuario, ventas);
        usuario.setActivo(true);
        if (put.password() != null && !put.password().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(put.password()));
        }
        repo.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Override
    public UsuarioGetDTO actualizarRol(Integer id, UsuarioRolDTO dto) {
        if (dto.rol() == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuarioLogueado = repo.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));
        if (usuarioLogueado.getId().equals(id)) {
            throw new IllegalStateException("No podés cambiar tu propio rol");
        }
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.getRol() == RolUsuario.ADMIN && dto.rol() != RolUsuario.ADMIN) {
            long admins = repo.countByRol(RolUsuario.ADMIN);
            if (admins <= 1) {
                throw new IllegalStateException("Debe existir al menos un ADMIN");
            }
        }

        usuario.setRol(dto.rol());
        repo.save(usuario);

        return mapper.toDTO(usuario);
    }


    @Override
    public Boolean existe(String dni) {
        return repo.existsByDniAndActivoTrue(dni);
    }

}

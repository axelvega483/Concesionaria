package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.entity.Venta;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioGetDTO toDTO(Usuario usuario) {
        List<UsuarioVentaDTO> ventas = Optional.ofNullable(usuario.getVentas()).orElse(Collections.emptyList())
                .stream().filter(Venta::isActivo).map(venta -> new UsuarioVentaDTO(
                        venta.getId(),
                        venta.getFecha(),
                        venta.getTotal())).collect(Collectors.toList());

        UsuarioGetDTO dto = new UsuarioGetDTO(usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getDni(),
                usuario.getRol(),
                usuario.isActivo(), ventas);
        return dto;
    }

    public Usuario toEntity(UsuarioPostDTO post) {
        return  Usuario.builder()
                .dni(post.dni())
                .email(post.email())
                .nombre(post.nombre())
                .password(post.password())
                .rol(post.rol())
                .activo(true)
                .build();
    }

    public Usuario fromUpdateDTO(UsuarioPutDTO update, Usuario usuario, List<Venta> ventas) {
        if (update.dni() != null) {
            usuario.setDni(update.dni());
        }
        if (update.email() != null) {
            usuario.setEmail(update.email());
        }
        if (update.nombre() != null) {
            usuario.setNombre(update.nombre());
        }
        if (update.rol() != null) {
            usuario.setRol(update.rol());
        }
        if (update.password() != null) {
            usuario.setPassword(update.password());
        }
        if (ventas != null) {
            usuario.setVentas(ventas);
        }
        return usuario;
    }

    public List<UsuarioGetDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream().filter(Usuario::isActivo).map(this::toDTO).toList();
    }
}

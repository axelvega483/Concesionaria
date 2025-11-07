package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.entity.Venta;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class UsuarioMapper {

    public UsuarioGetDTO toDTO(Usuario usuario) {
        UsuarioGetDTO dto = new UsuarioGetDTO();
        dto.setDni(usuario.getDni());
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());
        dto.setRol(usuario.getRol());
        dto.setId(usuario.getId());
        dto.setActivo(usuario.isActivo());


        List<UsuarioVentaDTO> ventas = Optional.ofNullable(usuario.getVentas()).orElse(Collections.emptyList())
                .stream().filter(Venta::isActivo).map(venta -> new UsuarioVentaDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal())).collect(Collectors.toList());

        dto.setVentas(ventas);
        return dto;
    }
    public Usuario toEntity(UsuarioPostDTO post) {
        Usuario usuario = new Usuario();
        usuario.setDni(post.getDni());
        usuario.setEmail(post.getEmail());
        usuario.setNombre(post.getNombre());
        usuario.setPassword(post.getPassword());
        usuario.setRol(post.getRol());
        usuario.setActivo(true);
        return usuario;
    }
    public Usuario fromUpdateDTO(UsuarioPutDTO update, Usuario usuario, List<Venta> ventas) {
        if(update.getDni() != null){
            usuario.setDni(update.getDni());
        }
        if(update.getEmail() != null){
            usuario.setEmail(update.getEmail());
        }
        if(update.getNombre() != null) {
            usuario.setNombre(update.getNombre());
        }
        if(update.getRol() != null) {
            usuario.setRol(update.getRol());
        }
        if(update.getPassword() != null) {
            usuario.setPassword(update.getPassword());
        }
        if(ventas != null) {
            usuario.setVentas(ventas);
        }
        return usuario;
    }
    public List<UsuarioGetDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream().filter(Usuario::isActivo).map(this::toDTO).toList();
    }
}

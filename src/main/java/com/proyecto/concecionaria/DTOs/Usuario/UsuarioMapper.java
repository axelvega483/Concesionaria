package com.proyecto.concecionaria.DTOs.Usuario;

import com.proyecto.concecionaria.entity.Usuario;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioGetDTO toDTO(Usuario usuario) {
        UsuarioGetDTO dto = new UsuarioGetDTO();
        dto.setDni(usuario.getDni());
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());
        dto.setRol(usuario.getRol());
        dto.setId(usuario.getId());
        dto.setActivo(usuario.getActivo());

        List<UsuarioVentaDTO> venta = Optional.ofNullable(usuario.getVentas()).orElse(Collections.emptyList())
                .stream().map(ventas -> new UsuarioVentaDTO(
                ventas.getId(),
                ventas.getFecha(),
                ventas.getTotal())).collect(Collectors.toList());

        dto.setVentas(venta);

        return dto;
    }
}

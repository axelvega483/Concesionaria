package com.proyecto.concecionaria.DTOs.Cliente;

import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Component
public class ClienteMapper {

    public ClienteGetDTO toDTO(Cliente cliente) {
        List<ClienteVentaDTO> ventas = Optional.ofNullable(cliente.getVentas()).orElse(Collections.emptyList())
                .stream().filter(Venta::isActivo).map(venta -> new ClienteVentaDTO(
                        venta.getId(),
                        venta.getFecha(),
                        venta.getTotal())).toList();

        return new ClienteGetDTO(cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDni(),
                cliente.isActivo(), ventas);

    }

    public Cliente toEntity(ClientePostDTO post) {
        return Cliente.builder()
                .nombre(post.nombre())
                .email(post.email())
                .dni(post.dni())
                .activo(true)
                .build();
    }

    public void updateEntityFromDTO(ClientePutDTO put, Cliente cliente) {
        if (put.nombre() != null) {
            cliente.setNombre(put.nombre());
        }
        if (put.dni() != null) {
            cliente.setDni(put.dni());
        }
        if (put.email() != null) {
            cliente.setEmail(put.email());
        }
    }

    public List<ClienteGetDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream().filter(Cliente::isActivo).map(this::toDTO).toList();
    }
}

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
        ClienteGetDTO dto = new ClienteGetDTO();
        dto.setId(cliente.getId());
        dto.setActivo(cliente.isActivo());
        dto.setDni(cliente.getDni());
        dto.setEmail(cliente.getEmail());
        dto.setNombre(cliente.getNombre());

        List<ClienteVentaDTO> ventas = Optional.ofNullable(cliente.getVentas()).orElse(Collections.emptyList())
                .stream().filter(Venta::isActivo).map(venta -> new ClienteVentaDTO(
                        venta.getId(),
                        venta.getFecha(),
                        venta.getTotal())).toList();
        dto.setVentas(ventas);
        return dto;
    }

    public Cliente toEntity(ClientePostDTO post) {
        Cliente cliente = new Cliente();
        cliente.setNombre(post.getNombre());
        cliente.setDni(post.getDni());
        cliente.setEmail(post.getEmail());
        cliente.setActivo(true);
        return cliente;
    }

    public Cliente updateEntityFromDTO(ClientePutDTO put, Cliente cliente) {
        if (put.getNombre() != null) {
            cliente.setNombre(put.getNombre());
        }
        if (put.getDni() != null) {
            cliente.setDni(put.getDni());
        }
        if (put.getEmail() != null) {
            cliente.setEmail(put.getEmail());
        }

        return cliente;
    }

    public List<ClienteGetDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream().filter(Cliente::isActivo).map(this::toDTO).toList();
    }
}

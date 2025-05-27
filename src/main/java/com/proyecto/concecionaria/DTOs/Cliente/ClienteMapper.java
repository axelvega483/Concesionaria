package com.proyecto.concecionaria.DTOs.Cliente;

import com.proyecto.concecionaria.entity.Cliente;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

public class ClienteMapper {

    public static ClienteGetDTO toDTO(Cliente cliente) {
        ClienteGetDTO dto = new ClienteGetDTO();
        dto.setId(cliente.getId());
        dto.setActivo(cliente.getActivo());
        dto.setDni(cliente.getDni());
        dto.setEmail(cliente.getEmail());
        dto.setNombre(cliente.getNombre());

        List<ClienteVentaDTO> venta = Optional.ofNullable(cliente.getVentas()).orElse(Collections.emptyList())
                .stream().map(ventas -> new ClienteVentaDTO(
                ventas.getId(), ventas.getFecha(), ventas.getTotal())).toList();
        dto.setVentas(venta);
        return dto;
    }
}

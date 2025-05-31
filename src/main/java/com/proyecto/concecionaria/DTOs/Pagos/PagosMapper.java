package com.proyecto.concecionaria.DTOs.Pagos;

import com.proyecto.concecionaria.entity.Pagos;

public class PagosMapper {

    public static PagosGetDTO toDTO(Pagos pagos) {
        PagosGetDTO dto = new PagosGetDTO();
        dto.setActivo(pagos.getActivo());
        dto.setEstado(pagos.getEstado());
        dto.setFechaPago(pagos.getFechaPago());
        dto.setId(pagos.getId());
        dto.setMetodoPago(pagos.getMetodoPago());
        dto.setMonto(pagos.getMonto());
        dto.setVenta(pagos.getVenta());
        return dto;
    }
}

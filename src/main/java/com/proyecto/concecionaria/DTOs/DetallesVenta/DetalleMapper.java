package com.proyecto.concecionaria.DTOs.DetallesVenta;

import com.proyecto.concecionaria.entity.DetalleVenta;

public class DetalleMapper {

    public static DetalleGetDTO toDTO(DetalleVenta detalleVenta) {
        DetalleGetDTO dto = new DetalleGetDTO();
        dto.setId(detalleVenta.getId());
        dto.setCantidad(detalleVenta.getCantidad());
        dto.setPrecioUnitario(detalleVenta.getPrecioUnitario());
        dto.setVehiculo(detalleVenta.getVehiculo());
        dto.setVenta(detalleVenta.getVenta());
        return dto;
    }
}

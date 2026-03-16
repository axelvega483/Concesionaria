package com.proyecto.concecionaria.DTOs.DetallesVenta;

import com.proyecto.concecionaria.entity.DetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class DetalleMapper {

    public static DetalleGetDTO toDTO(DetalleVenta detalleVenta) {
        return new DetalleGetDTO(
                detalleVenta.getId(),
                detalleVenta.getVehiculo(),
                detalleVenta.getCantidad(),
                detalleVenta.getPrecioUnitario(),
                detalleVenta.getVenta()
        );
    }
}

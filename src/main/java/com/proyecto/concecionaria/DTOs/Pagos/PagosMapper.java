package com.proyecto.concecionaria.DTOs.Pagos;

import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.entity.Venta;

public class PagosMapper {

    public static PagosGetDTO toDTO(Pagos pagos) {
        PagosGetDTO dto = new PagosGetDTO();
        dto.setActivo(pagos.getActivo());
        dto.setEstado(pagos.getEstado());
        dto.setFechaPago(pagos.getFechaPago());
        dto.setId(pagos.getId());
        dto.setMetodoPago(pagos.getMetodoPago());
        dto.setMonto(pagos.getMonto());
        Venta venta = pagos.getVenta();
        if (venta != null) {
            PagoVenta ventaDTO = new PagoVenta();
            ventaDTO.setId(venta.getId());
            ventaDTO.setTotal(venta.getTotal());
            dto.setVenta(ventaDTO);
        }
        return dto;
    }
}

package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.entity.Venta;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VentaMapper {

    public static VentaGetDTO toDTO(Venta venta) {
        VentaGetDTO dto = new VentaGetDTO();
        dto.setActivo(venta.getActivo());
        dto.setCliente(venta.getCliente());
        dto.setEmpleado(venta.getEmpleado());
        dto.setFrecuenciaPago(venta.getFrecuenciaPago());
        dto.setFecha(venta.getFecha());
        dto.setId(venta.getId());
        dto.setTotal(venta.getTotal());
        dto.setCuotas(venta.getCuotas());
        dto.setEstado(venta.getEstado());
        dto.setEntrega(venta.getEntrega());

        List<VentaDetalleDTO> detalles = Optional.ofNullable(venta.getDetalleVentas()).orElse(Collections.emptyList())
                .stream()
                .map(detalle -> new VentaDetalleDTO(
                detalle.getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario()))
                .toList();
        dto.setDetalleVentas(detalles);

        List<VentaPagosDTO> pagos = Optional.ofNullable(venta.getPagos()).orElse(Collections.emptyList())
                .stream()
                .map(pago -> new VentaPagosDTO(
                pago.getId(),
                pago.getFechaPago(),
                pago.getMonto()))
                .toList();
        dto.setPagos(pagos);

        return dto;
    }
}

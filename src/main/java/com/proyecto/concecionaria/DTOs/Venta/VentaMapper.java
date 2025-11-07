package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.entity.*;
import com.proyecto.concecionaria.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class VentaMapper {
    @Autowired
    private VehiculoRepository vehiculoRepo;

    public VentaGetDTO toDTO(Venta venta) {
        VentaGetDTO dto = new VentaGetDTO();
        dto.setActivo(venta.isActivo());
        dto.setCliente(venta.getCliente());
        dto.setEmpleado(venta.getEmpleado());
        dto.setFrecuenciaPago(venta.getFrecuenciaPago());
        dto.setFecha(venta.getFecha());
        dto.setId(venta.getId());
        dto.setTotal(venta.getTotal());
        dto.setCuotas(venta.getCuotas());
        dto.setEstado(venta.getEstado());
        dto.setEntrega(venta.getEntrega());

        List<VentaDetalleDTO> detalles = Optional.ofNullable(venta.getDetalleVentas()).orElse(Collections.emptyList()).stream().map(detalle -> new VentaDetalleDTO(detalle.getId(), detalle.getCantidad(), detalle.getPrecioUnitario())).toList();
        dto.setDetalleVentas(detalles);

        List<VentaPagosDTO> pagos = Optional.ofNullable(venta.getPagos()).orElse(Collections.emptyList())
                .stream().map(pago -> new VentaPagosDTO(
                        pago.getId(),
                        pago.getFechaPago(),
                        pago.getMonto(),
                        pago.getEstado()))
                .toList();
        dto.setPagos(pagos);

        return dto;
    }

    public Venta toEntity(VentaPostDTO ventaDTO, Cliente cliente, Usuario empleado) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setFrecuenciaPago(ventaDTO.getFrecuenciaPago());
        venta.setFecha(LocalDate.now());
        venta.setTotal(ventaDTO.getTotal());
        venta.setCuotas(ventaDTO.getCuotas());
        venta.setEstado(ventaDTO.getEstado());
        venta.setEntrega(ventaDTO.getEntrega());
        venta.setActivo(true);
        venta.setDetalleVentas(mapearDetalles(ventaDTO.getDetalleVentas(), venta));

        venta.generarPagos();
        return venta;
    }

    public List<VentaGetDTO> toDTOList(List<Venta> ventas) {
        return ventas.stream().filter(Venta::isActivo).map(this::toDTO).toList();
    }

    private List<DetalleVenta> mapearDetalles(List<DetalleVentaPostDTO> detalleDTOs, Venta venta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        for (DetalleVentaPostDTO dto : detalleDTOs) {
            Integer idVehiculo = dto.getVehiculoId();
            if (idVehiculo == null) {
                throw new IllegalArgumentException("El ID del vehículo no puede ser nulo");
            }
            Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + idVehiculo));
            if (vehiculo.getId() == null) {
                throw new RuntimeException("Vehículo obtenido tiene ID nulo: " + idVehiculo);
            }
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVehiculo(vehiculo);
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setVenta(venta);

            detalles.add(detalle);
        }

        return detalles;
    }
}

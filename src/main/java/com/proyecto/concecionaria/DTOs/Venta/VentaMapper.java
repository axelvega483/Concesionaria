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
        List<VentaDetalleDTO> detalles = Optional.ofNullable(venta.getDetalleVentas()).orElse(Collections.emptyList()).stream().map(detalle -> new VentaDetalleDTO(detalle.getId(), detalle.getCantidad(), detalle.getPrecioUnitario())).toList();
        List<VentaPagosDTO> pagos = Optional.ofNullable(venta.getPagos()).orElse(Collections.emptyList())
                .stream().map(pago -> new VentaPagosDTO(
                        pago.getId(),
                        pago.getFechaPago(),
                        pago.getMonto(),
                        pago.getEstado()))
                .toList();
        return new VentaGetDTO(venta.getId(),
                venta.getFecha(),
                venta.getFrecuenciaPago(),
                venta.getTotal(),
                detalles,
                venta.getCliente(),
                venta.getEmpleado(),
                pagos,
                venta.isActivo(),
                venta.getEntrega(),
                venta.getEstado(),
                venta.getCuotas());
    }

    public Venta toEntity(VentaPostDTO ventaDTO, Cliente cliente, Usuario empleado) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setFrecuenciaPago(ventaDTO.frecuenciaPago());
        venta.setFecha(LocalDate.now());
        venta.setTotal(ventaDTO.total());
        venta.setCuotas(ventaDTO.cuotas());
        venta.setEstado(ventaDTO.estado());
        venta.setEntrega(ventaDTO.entrega());
        venta.setActivo(true);
        venta.setDetalleVentas(mapearDetalles(ventaDTO.detalleVentas(), venta));

        venta.generarPagos();
        return venta;
    }

    public List<VentaGetDTO> toDTOList(List<Venta> ventas) {
        return ventas.stream().filter(Venta::isActivo).map(this::toDTO).toList();
    }

    private List<DetalleVenta> mapearDetalles(List<DetalleVentaPostDTO> detalleDTOs, Venta venta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        for (DetalleVentaPostDTO dto : detalleDTOs) {
            Integer idVehiculo = dto.vehiculoId();
            if (idVehiculo == null) {
                throw new IllegalArgumentException("El ID del vehículo no puede ser nulo");
            }
            Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo).orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + idVehiculo));
            if (vehiculo.getId() == null) {
                throw new RuntimeException("Vehículo obtenido tiene ID nulo: " + idVehiculo);
            }
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVehiculo(vehiculo);
            detalle.setCantidad(dto.cantidad());
            detalle.setPrecioUnitario(dto.precioUnitario());
            detalle.setVenta(venta);

            detalles.add(detalle);
        }

        return detalles;
    }
}

package com.proyecto.concecionaria.DTOs.Pagos;

import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.util.EstadoPagos;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.MetodoPago;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PagosMapper {

    public PagosGetDTO toDTO(Pagos pagos) {

        PagoVenta ventaDTO = null;

        Venta venta = pagos.getVenta();
        if (venta != null) {
            ventaDTO = new PagoVenta(
                    venta.getId(),
                    venta.getTotal()
            );
        }

        return new PagosGetDTO(
                pagos.getId(),
                pagos.getFechaPago(),
                pagos.getMetodoPago(),
                pagos.getMonto(),
                ventaDTO,
                pagos.getEstado(),
                pagos.isActivo()
        );
    }

    public Pagos cancelar(Pagos pago) {
        pago.setMetodoPago(MetodoPago.PENDIENTE);
        pago.setFechaPago(null);
        pago.setEstado(EstadoPagos.PENDIENTE);
        pago.setActivo(true);

        if (pago.getVenta() != null) {
            pago.getVenta().setEstado(EstadoVenta.ACTIVO);
            pago.getVenta().setActivo(true);
        }
        return pago;

    }

    public Pagos confimar(Pagos pagos, PagosPutDTO putDTO) {
        pagos.setFechaPago(LocalDate.now());
        pagos.setMetodoPago(putDTO.metodoPago());
        pagos.confirmarPago();
        return pagos;
    }

    public List<PagosGetDTO> toList(List<Pagos> pagosList) {
        return pagosList.stream().map(this::toDTO).toList();
    }
}

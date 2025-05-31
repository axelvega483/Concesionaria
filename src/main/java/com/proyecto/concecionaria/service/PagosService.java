package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.repository.PagosRepository;
import com.proyecto.concecionaria.util.EstadoPagos;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagosService implements PagosInterfaz {

    @Autowired
    private PagosRepository repo;
    @Autowired
    private VentaService ventaService;

    @Override
    public Pagos guardar(Pagos pagos) {
        return repo.save(pagos);
    }

    @Override
    public Optional<Pagos> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Pagos> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(pagos -> {
            pagos.setActivo(Boolean.FALSE);
            repo.save(pagos);
        });
    }

    public List<Pagos> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }

    public Pagos confirmarPago(Integer id, String metodoPago) {
        Pagos pagos = obtener(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        if (pagos.getEstado() == EstadoPagos.PAGADO) {
            throw new IllegalStateException("El pago ya se encuentra realizado");
        }
        pagos.setMetodoPago(metodoPago);
        pagos.confirmarPago();
        pagos = guardar(pagos);
        ventaService.guardar(pagos.getVenta());

        return pagos;
    }

    public Pagos cancelarPago(Integer id) {
        Pagos pago = obtener(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        if (pago.getEstado() == EstadoPagos.PAGADO) {
            throw new IllegalStateException("No se puede anular un pago ya confirmado");
        }

        pago.setEstado(EstadoPagos.PENDIENTE);
        pago.setFechaPago(null);
        pago.setMetodoPago(null);
        pago.setActivo(true);
        pago = guardar(pago);
        pago.getVenta().actualizarSaldo();
        ventaService.guardar(pago.getVenta());

        return pago;
    }
}

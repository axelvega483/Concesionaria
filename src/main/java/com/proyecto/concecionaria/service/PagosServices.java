package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.repository.PagosRepository;
import com.proyecto.concecionaria.util.EstadoPagos;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.MetodoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagosServices implements PagosInterfaz {
    @Autowired
    private PagosRepository repo;

    @Override
    public Optional<Pagos> findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Pagos> findByAll() {
        return repo.findAll();
    }

    @Override
    public void cancelar(Integer id) {
        Optional<Pagos> pagosOptional = findById(id);
        if(pagosOptional.isPresent()){
            Pagos pago = pagosOptional.get();
            pago.setMetodoPago(MetodoPago.PENDIENTE);
            pago.setFechaPago(null);
            pago.setEstado(EstadoPagos.PENDIENTE);
            pago.setActivo(Boolean.TRUE);
            pago.getVenta().setEstado(EstadoVenta.ACTIVO);
            pago.getVenta().setActivo(Boolean.TRUE);
            repo.save(pago);
        }
    }

    @Override
    public Pagos save(Pagos pagos) {
        return repo.save(pagos);
    }
}

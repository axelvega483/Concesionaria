package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.DTOs.Pagos.PagosGetDTO;
import com.proyecto.concecionaria.DTOs.Pagos.PagosMapper;
import com.proyecto.concecionaria.DTOs.Pagos.PagosPutDTO;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagosServices implements PagosInterfaz {
    @Autowired
    private PagosRepository repo;
    @Autowired
    private PagosMapper mapper;

    @Override
    public Optional<PagosGetDTO> findById(Integer id) {
        return repo.findById(id).filter(Pagos::isActivo).map(mapper::toDTO);
    }

    @Override
    public List<PagosGetDTO> findByAll() {
        return mapper.toList(repo.findAll());
    }

    @Override
    public PagosGetDTO cancelar(Integer id) {
        Pagos pago = repo.findById(id).filter(Pagos::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));
        Pagos pagoCancelado = mapper.cancelar(pago);
        Pagos pagoGuardado = repo.save(pagoCancelado);
        return mapper.toDTO(pagoGuardado);
    }

    @Override
    public Pagos save(Pagos pagos) {
        return repo.save(pagos);
    }

    @Override
    public PagosGetDTO confirmarPago(Integer id, PagosPutDTO putDTO) {
        Pagos pagos = repo.findById(id).filter(Pagos::isActivo)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));
        Pagos pagoConfirmado = mapper.confimar(pagos, putDTO);
        Pagos pagoGuardado = repo.save(pagoConfirmado);
        return mapper.toDTO(pagoGuardado);
    }

    @Override
    public Optional<Pagos> findEntityById(Integer id) {
        return repo.findById(id).filter(Pagos::isActivo);
    }
}

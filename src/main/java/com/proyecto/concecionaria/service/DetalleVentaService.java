package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.DetalleVenta;
import com.proyecto.concecionaria.repository.DetalleVentaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository repo;

    public List<DetalleVenta> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }
}

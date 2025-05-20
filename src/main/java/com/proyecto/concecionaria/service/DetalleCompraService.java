package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.DetalleCompra;
import com.proyecto.concecionaria.repository.DetalleCompraRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetalleCompraService {

    private final DetalleCompraRepository repo;

    public List<DetalleCompra> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }
}

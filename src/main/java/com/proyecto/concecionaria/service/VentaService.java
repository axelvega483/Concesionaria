package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.interfaz.VentaInterfaz;
import com.proyecto.concecionaria.repository.VentaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VentaService implements VentaInterfaz {

    private final VentaRepository repo;

    @Override
    public Venta guardar(Venta venta) {
        return repo.save(venta);
    }

    @Override
    public Optional<Venta> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Venta> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(venta -> {
            venta.setActivo(Boolean.FALSE);
            repo.save(venta);
        });
    }

    public List<Venta> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }
}

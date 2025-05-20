package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Compra;
import com.proyecto.concecionaria.interfaz.CompraInterfaz;
import com.proyecto.concecionaria.repository.CompraRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompraService implements CompraInterfaz {

    private final CompraRepository repo;

    @Override
    public Compra guardar(Compra compra) {
        return repo.save(compra);
    }

    @Override
    public Optional<Compra> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Compra> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(compra -> {
            compra.setActivo(Boolean.FALSE);
            repo.save(compra);
        });
    }

}

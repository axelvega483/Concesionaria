package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.repository.PagosRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagosService implements PagosInterfaz {

    private final PagosRepository repo;

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
}

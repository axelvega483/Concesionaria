package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.interfaz.ClienteInterfaz;
import com.proyecto.concecionaria.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteInterfaz {

    private final ClienteRepository repo;

    @Override
    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    @Override
    public Optional<Cliente> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Cliente> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(cliente -> {
            cliente.setActivo(Boolean.FALSE);
            repo.save(cliente);
        });
    }

}

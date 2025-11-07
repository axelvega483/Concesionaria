package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.DTOs.Cliente.ClienteGetDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClienteMapper;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePostDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePutDTO;
import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.interfaz.ClienteInterfaz;
import com.proyecto.concecionaria.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

import com.proyecto.concecionaria.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements ClienteInterfaz {
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private ClienteMapper mapper;
    @Autowired
    private VentaRepository ventaRepo;

    @Override
    public ClienteGetDTO crear(ClientePostDTO post) {
        if(existe(post.getDni())){
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI.");
        }
        Cliente cliente = mapper.toEntity(post);
        repo.save(cliente);
        return mapper.toDTO(cliente);
    }

    @Override
    public Optional<ClienteGetDTO> obtener(Integer id) {
        return repo.findById(id)
                .filter(Cliente::isActivo)
                .map(mapper::toDTO);
    }

    @Override
    public List<ClienteGetDTO> listar() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public ClienteGetDTO actualizar(Integer id, ClientePutDTO put) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        mapper.updateEntityFromDTO(put, cliente);
        cliente.setActivo(true);
        Cliente actualizado = repo.save(cliente);
        return mapper.toDTO(actualizado);
    }

    @Override
    public ClienteGetDTO eliminar(Integer id) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        cliente.setActivo(false);
        repo.save(cliente);
        return mapper.toDTO(cliente);
    }

    public Boolean existe(String dni) {
        return repo.findByDniAndActivo(dni).isPresent();
    }
}

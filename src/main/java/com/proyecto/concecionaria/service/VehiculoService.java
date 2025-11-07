package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoGetDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoMapper;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPostDTO;
import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.interfaz.VehiculoInterfaz;
import com.proyecto.concecionaria.repository.VehiculoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService implements VehiculoInterfaz {

    @Autowired
    private VehiculoRepository repo;
    @Autowired
    private VehiculoMapper mapper;

    @Override
    public VehiculoGetDTO guardar(Vehiculo vehiculo) {
        return mapper.toDTO(repo.save(vehiculo));
    }

    @Override
    public VehiculoGetDTO crear(VehiculoPostDTO post) {
        Optional<Vehiculo> vehiculoExistente = buscarPorMarcaModeloAnio(post.getMarca(), post.getModelo(), post.getAnioModelo());

        if (vehiculoExistente.isPresent()) {
            Vehiculo vehiculo = vehiculoExistente.get();
            Integer stockActual = vehiculo.getStock();
            vehiculo.setStock(stockActual + post.getStock());
            Vehiculo vehiculoActualizado = repo.save(vehiculo);
            return mapper.toDTO(vehiculoActualizado);
        } else {
            Vehiculo vehiculo = mapper.toEntity(post);
            Vehiculo vehiculoGuardado = repo.save(vehiculo);
            return mapper.toDTO(vehiculoGuardado);
        }
    }

    @Override
    public Optional<VehiculoGetDTO> obtener(Integer id) {
        return repo.findById(id).filter(Vehiculo::isActivo).map(mapper::toDTO);
    }

    @Override
    public List<VehiculoGetDTO> listar() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public VehiculoGetDTO eliminar(Integer id) {
        Vehiculo vehiculo = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        vehiculo.setActivo(false);
        repo.save(vehiculo);
        return mapper.toDTO(vehiculo);
    }

    @Override
    public VehiculoGetDTO actualizar(Integer id, com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPutDTO put) {
        Vehiculo vehiculo = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        mapper.updateEntityFromDTO(put, vehiculo);
        vehiculo.setActivo(true);
        repo.save(vehiculo);
        return mapper.toDTO(vehiculo);
    }

    public Optional<Vehiculo> buscarPorMarcaModeloAnio(String marca, String modelo, Integer anioModelo) {
        return repo.findByMarcaAndModeloAndAnioModelo(marca, modelo, anioModelo);
    }
    public Optional<Vehiculo> obtenerEntidad(Integer id) {
        return repo.findById(id).filter(Vehiculo::isActivo);
    }
}

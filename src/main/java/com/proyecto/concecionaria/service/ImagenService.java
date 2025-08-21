package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Imagen;
import com.proyecto.concecionaria.interfaz.ImagenIntefaz;
import com.proyecto.concecionaria.repository.ImagenRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImagenService implements ImagenIntefaz {
    @Autowired
    private ImagenRepository repo;

    @Override
    public Imagen guardar(Imagen imagen) {
        return repo.save(imagen);
    }

    @Override
    public Optional<Imagen> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Imagen> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

}

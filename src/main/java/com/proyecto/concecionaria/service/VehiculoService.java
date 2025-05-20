package com.proyecto.concecionaria.service;

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

    @Override
    public Vehiculo guardar(Vehiculo vehiculo) {
        return repo.save(vehiculo);
    }

    @Override
    public Optional<Vehiculo> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Vehiculo> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Vehiculo> vehiOpt = repo.findById(id);
        if (vehiOpt.isPresent()) {
            Vehiculo vehiculo = vehiOpt.get();
            vehiculo.setActivo(Boolean.FALSE);
            repo.save(vehiculo);
        }

    }

}

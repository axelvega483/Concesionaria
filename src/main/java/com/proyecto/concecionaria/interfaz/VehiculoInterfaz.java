package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Vehiculo;
import java.util.List;
import java.util.Optional;

public interface VehiculoInterfaz {

    public Vehiculo guardar(Vehiculo vehiculo);

    public Optional<Vehiculo> obtener(Integer id);

    public List<Vehiculo> listar();

    public void eliminar(Integer id);
}

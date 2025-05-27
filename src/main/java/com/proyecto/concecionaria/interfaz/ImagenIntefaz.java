package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Imagen;
import java.util.List;
import java.util.Optional;


public interface ImagenIntefaz {
     public Imagen guardar(Imagen imagen);

    public Optional<Imagen> obtener(Integer id);

    public List<Imagen> listar();

    public void eliminar(Integer id);
}

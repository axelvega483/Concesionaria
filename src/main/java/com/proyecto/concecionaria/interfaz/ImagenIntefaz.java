package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Imagen;
import java.util.List;
import java.util.Optional;


public interface ImagenIntefaz {

     Optional<Imagen> obtener(Integer id);

     List<Imagen> listar();

     void eliminar(Integer id);
}

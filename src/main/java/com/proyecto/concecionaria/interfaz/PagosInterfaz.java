package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Pagos;
import java.util.List;
import java.util.Optional;

public interface PagosInterfaz {

    public Pagos guardar(Pagos pagos);

    public Optional<Pagos> obtener(Integer id);

    public List<Pagos> listar();

    public void eliminar(Integer id);

}

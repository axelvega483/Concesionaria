package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Pagos;

import java.util.List;
import java.util.Optional;

public interface PagosInterfaz {

    public Optional<Pagos> findById(Integer id);

    public List<Pagos> findByAll();

    public void cancelar(Integer id);

    public Pagos save(Pagos pagos);


}

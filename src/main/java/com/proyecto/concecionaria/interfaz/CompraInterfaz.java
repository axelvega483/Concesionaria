package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Compra;
import java.util.List;
import java.util.Optional;

public interface CompraInterfaz {

    public Compra guardar(Compra compra);

    public Optional<Compra> obtener(Integer id);

    public List<Compra> listar();

    public void eliminar(Integer id);
}

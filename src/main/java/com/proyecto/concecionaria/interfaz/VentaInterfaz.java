package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaInterfaz {

    public Venta guardar(Venta venta);

    public Optional<Venta> obtener(Integer id);

    public List<Venta> listar();

    public void eliminar(Integer id);
}

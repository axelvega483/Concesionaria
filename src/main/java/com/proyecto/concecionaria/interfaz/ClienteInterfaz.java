package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteInterfaz {

    public Cliente guardar(Cliente cliente);

    public Optional<Cliente> obtener(Integer id);

    public List<Cliente> listar();

    public void eliminar(Integer id);
}

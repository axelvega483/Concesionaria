package com.proyecto.concecionaria.interfaz;

import com.proyecto.concecionaria.DTOs.Pagos.PagosGetDTO;
import com.proyecto.concecionaria.DTOs.Pagos.PagosPutDTO;
import com.proyecto.concecionaria.entity.Pagos;

import java.util.List;
import java.util.Optional;

public interface PagosInterfaz {

    Optional<PagosGetDTO> findById(Integer id);

    List<PagosGetDTO> findByAll();

    PagosGetDTO cancelar(Integer id);

    Pagos save(Pagos pagos);

    PagosGetDTO confirmarPago(Integer id, PagosPutDTO putDTO);

    Optional<Pagos> findEntityById(Integer id);
}

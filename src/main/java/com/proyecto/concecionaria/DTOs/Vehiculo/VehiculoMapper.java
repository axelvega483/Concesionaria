package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.entity.Imagen;
import com.proyecto.concecionaria.entity.Vehiculo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VehiculoMapper {

    public static VehiculoGetDTO toDTO(Vehiculo vehiculo) {
        VehiculoGetDTO dto = new VehiculoGetDTO();
        dto.setId(vehiculo.getId());
        dto.setActivo(vehiculo.getActivo());
        dto.setAnioModelo(vehiculo.getAnioModelo());
        dto.setEstado(vehiculo.getEstado());
        dto.setKilometraje(vehiculo.getKilometraje());
        dto.setMarca(vehiculo.getMarca());
        dto.setModelo(vehiculo.getModelo());
        dto.setPrecio(vehiculo.getPrecio());
        dto.setStock(vehiculo.getStock());
        dto.setTipo(vehiculo.getTipo());

        List<String> imagenes = Optional.ofNullable(vehiculo.getImagenes()).orElse(Collections.emptyList())
                .stream()
                .map(imagen -> "/api/vehiculo/imagen/" + imagen.getNombre())
                .collect(Collectors.toList());

        dto.setImagenes(imagenes);

        List<VehiculoVentaDetalleDTO> detalles = Optional.ofNullable(vehiculo.getDetalleVentas()).orElse(Collections.emptyList())
                .stream()
                .map(detalle -> new VehiculoVentaDetalleDTO(
                detalle.getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario())).toList();
        dto.setDetalleVentas(detalles);
        return dto;
    }
}

package com.proyecto.concecionaria.DTOs.Vehiculo;

import com.proyecto.concecionaria.entity.Imagen;
import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.entity.Venta;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class VehiculoMapper {

    public  VehiculoGetDTO toDTO(Vehiculo vehiculo) {
        VehiculoGetDTO dto = new VehiculoGetDTO();
        dto.setId(vehiculo.getId());
        dto.setActivo(vehiculo.isActivo());
        dto.setAnioModelo(vehiculo.getAnioModelo());
        dto.setEstado(vehiculo.getEstado());
        dto.setKilometraje(vehiculo.getKilometraje());
        dto.setMarca(vehiculo.getMarca());
        dto.setModelo(vehiculo.getModelo());
        dto.setPrecio(vehiculo.getPrecio());
        dto.setStock(vehiculo.getStock());
        dto.setTipo(vehiculo.getTipo());
        dto.setColor(vehiculo.getColor());
        List<String> imagenes = Optional.ofNullable(vehiculo.getImagenes()).orElse(Collections.emptyList())
                .stream()
                .map(imagen -> "/api/vehiculo/imagen/" + imagen.getId())
                .collect(Collectors.toList());

        dto.setImagenes(imagenes);

        List<VehiculoVentaDetalleDTO> detalles = Optional.ofNullable(vehiculo.getDetalleVentas()).orElse(Collections.emptyList())
                .stream().filter(detalle -> detalle.getVenta().isActivo())
                .map(detalle -> new VehiculoVentaDetalleDTO(
                        detalle.getId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario()))
                .toList();
        dto.setDetalleVentas(detalles);
        return dto;
    }
    public Vehiculo toEntity(VehiculoPostDTO post){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setAnioModelo(post.getAnioModelo());
        vehiculo.setEstado(post.getEstado());
        vehiculo.setKilometraje(post.getKilometraje());
        vehiculo.setMarca(post.getMarca());
        vehiculo.setModelo(post.getModelo());
        vehiculo.setPrecio(post.getPrecio());
        vehiculo.setStock(post.getStock());
        vehiculo.setTipo(post.getTipo());
        vehiculo.setActivo(true);
        vehiculo.setColor(post.getColor());
        List<String> nombresImagenes = Optional.ofNullable(post.getNombresImagenes()).orElse(Collections.emptyList());
        for (String nombre : nombresImagenes) {
            Imagen imagen = new Imagen();
            imagen.setNombre(nombre);
            vehiculo.addImagen(imagen);
        }
        return vehiculo;
    }
    public Vehiculo updateEntityFromDTO(VehiculoPutDTO put, Vehiculo vehiculo){
        if(put.getAnioModelo() != null){
            vehiculo.setAnioModelo(put.getAnioModelo());
        }
        if(put.getEstado() != null){
            vehiculo.setEstado(put.getEstado());
        }
        if(put.getKilometraje() != null){
            vehiculo.setKilometraje(put.getKilometraje());
        }
        if(put.getMarca() != null){
            vehiculo.setMarca(put.getMarca());
        }
        if(put.getModelo() != null){
            vehiculo.setModelo(put.getModelo());
        }
        if(put.getPrecio() != null){
            vehiculo.setPrecio(put.getPrecio());
        }
        if(put.getStock() != null){
            vehiculo.setStock(put.getStock());
        }
        if(put.getTipo() != null){
            vehiculo.setTipo(put.getTipo());
        }
        if(put.getColor() != null){
            vehiculo.setColor(put.getColor());
        }
        return vehiculo;
    }
    public List<VehiculoGetDTO> toDTOList(List<Vehiculo> vehiculos){
        return vehiculos.stream().filter(Vehiculo::isActivo).map(this::toDTO).toList();
    }
}

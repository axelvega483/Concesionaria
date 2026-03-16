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
        List<String> imagenes = Optional.ofNullable(vehiculo.getImagenes()).orElse(Collections.emptyList())
                .stream()
                .map(imagen -> "/api/vehiculo/imagen/" + imagen.getId())
                .collect(Collectors.toList());
        List<VehiculoVentaDetalleDTO> detalles = Optional.ofNullable(vehiculo.getDetalleVentas()).orElse(Collections.emptyList())
                .stream().filter(detalle -> detalle.getVenta().isActivo())
                .map(detalle -> new VehiculoVentaDetalleDTO(
                        detalle.getId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario()))
                .toList();

        return new VehiculoGetDTO(vehiculo.getId(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnioModelo(),
                vehiculo.getPrecio(),
                vehiculo.getStock(),
                vehiculo.getColor(),
                vehiculo.getTipo(),
                vehiculo.getEstado(),
                vehiculo.getKilometraje(),
                imagenes,
                vehiculo.isActivo(),
                detalles);
    }
    public Vehiculo toEntity(VehiculoPostDTO post){
        List<Imagen> imagenes = Optional.ofNullable(post.nombresImagenes())
                .orElse(Collections.emptyList())
                .stream()
                .map(nombre -> {
                    Imagen imagen = new Imagen();
                    imagen.setNombre(nombre);
                    return imagen;
                })
                .toList();

        return Vehiculo.builder()
                .anioModelo(post.anioModelo())
                .estado(post.estado())
                .kilometraje(post.kilometraje())
                .marca(post.marca())
                .modelo(post.modelo())
                .precio(post.precio())
                .stock(post.stock())
                .tipo(post.tipo())
                .activo(true)
                .color(post.color())
                .imagenes(imagenes)
                .build();

    }
    public void updateEntityFromDTO(VehiculoPutDTO put, Vehiculo vehiculo){
        if(put.anioModelo() != null){
            vehiculo.setAnioModelo(put.anioModelo());
        }
        if(put.estado() != null){
            vehiculo.setEstado(put.estado());
        }
        if(put.kilometraje() != null){
            vehiculo.setKilometraje(put.kilometraje());
        }
        if(put.marca() != null){
            vehiculo.setMarca(put.marca());
        }
        if(put.modelo() != null){
            vehiculo.setModelo(put.modelo());
        }
        if(put.precio() != null){
            vehiculo.setPrecio(put.precio());
        }
        if(put.stock() != null){
            vehiculo.setStock(put.stock());
        }
        if(put.tipo() != null){
            vehiculo.setTipo(put.tipo());
        }
        if(put.color() != null){
            vehiculo.setColor(put.color());
        }
    }
    public List<VehiculoGetDTO> toDTOList(List<Vehiculo> vehiculos){
        return vehiculos.stream().filter(Vehiculo::isActivo).map(this::toDTO).toList();
    }
}

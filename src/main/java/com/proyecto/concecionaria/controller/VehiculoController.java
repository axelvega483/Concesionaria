package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoGetDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoMapper;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPostDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPutDTO;
import com.proyecto.concecionaria.entity.DetalleVenta;
import com.proyecto.concecionaria.entity.Imagen;
import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.interfaz.ImagenIntefaz;
import com.proyecto.concecionaria.service.DetalleVentaService;
import com.proyecto.concecionaria.service.VehiculoService;
import com.proyecto.concecionaria.util.ApiResponse;

import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private ImagenIntefaz imagenService;
    @Autowired
    private DetalleVentaService detalleService;

    @Value("${app.ruta.imagenes}")
    private String rutaImagenes;

    @GetMapping
    public ResponseEntity<?> listarVehiculos() {
        try {
            List<VehiculoGetDTO> dto = vehiculoService.listar()
                    .stream()
                    .map(VehiculoMapper::toDTO).toList();
            return new ResponseEntity<>(new ApiResponse<>("Listado de vehiculos ", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerVehiculo(@PathVariable Integer id) {
        try {
            Vehiculo vehiculo = vehiculoService.obtener(id).orElse(null);
            if (vehiculo != null) {
                VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculo);
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo encontrado ", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro vehiculo", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<Resource> verImagen(@PathVariable Integer id) {
        try {
            Imagen imagen = imagenService.obtener(id).orElse(null);
            if (imagen == null) {
                return ResponseEntity.notFound().build();
            }

            Path ruta = Paths.get(rutaImagenes).resolve(imagen.getNombre()).toAbsolutePath();
            System.out.println("Buscando imagen en: " + ruta);

            Resource recurso = new UrlResource(ruta.toUri());
            if (!recurso.exists() || !recurso.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String extension = FilenameUtils.getExtension(imagen.getNombre()).toLowerCase();
            MediaType mediaType = switch (extension) {
                case "png" -> MediaType.IMAGE_PNG;
                case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
                case "gif" -> MediaType.IMAGE_GIF;
                default -> MediaType.APPLICATION_OCTET_STREAM;
            };

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(recurso);

        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @PostMapping
    public ResponseEntity<?> crearVehiculo(@Valid @RequestBody VehiculoPostDTO vehiculoDTO) {
        try {
            if (vehiculoService.existe(vehiculoDTO.getMarca(), vehiculoDTO.getModelo(), vehiculoDTO.getAnioModelo())) {
                Optional<Vehiculo> vehiculoOptional = vehiculoService.buscar(vehiculoDTO.getMarca(), vehiculoDTO.getModelo(), vehiculoDTO.getAnioModelo());
                Vehiculo vehiculo = vehiculoOptional.get();
                Integer stock = vehiculo.getStock();
                vehiculo.setStock(stock + vehiculoDTO.getStock());
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo ya existente, se actualizo el stock", vehiculoDTO, true), HttpStatus.OK);
            } else {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setActivo(vehiculoDTO.getActivo());
                vehiculo.setAnioModelo(vehiculoDTO.getAnioModelo());
                vehiculo.setColor(vehiculoDTO.getColor());
                vehiculo.setEstado(vehiculoDTO.getEstado());
                vehiculo.setKilometraje(vehiculoDTO.getKilometraje());
                vehiculo.setMarca(vehiculoDTO.getMarca());
                vehiculo.setModelo(vehiculoDTO.getModelo());
                vehiculo.setPrecio(vehiculoDTO.getPrecio());
                vehiculo.setStock(vehiculoDTO.getStock());
                vehiculo.setTipo(vehiculoDTO.getTipo());

                List<DetalleVenta> detalle = detalleService.obtenerById(vehiculoDTO.getDetalleVentasId());
                if (detalle.size() != vehiculoDTO.getDetalleVentasId().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Una o más detalles no existen", null, false), HttpStatus.BAD_REQUEST);
                }
                vehiculo.setDetalleVentas(detalle);
                List<String> nombresImagenes = Optional.ofNullable(vehiculoDTO.getNombresImagenes()).orElse(Collections.emptyList());
                for (String nombre : nombresImagenes) {
                    Imagen imagen = new Imagen();
                    imagen.setNombre(nombre);
                    vehiculo.addImagen(imagen);
                }
                VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculoService.guardar(vehiculo));
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo creado con existo", dto, true), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "subir-imagen/{idVehiculo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirImagenesVehiculo(@PathVariable Integer idVehiculo,
                                                   @RequestParam("imagenes") MultipartFile[] imagenes) {
        try {
            Optional<Vehiculo> optional = vehiculoService.obtener(idVehiculo);
            if (optional.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>("Vehículo no encontrado", null, false), HttpStatus.NOT_FOUND);
            }

            Vehiculo vehiculo = optional.get();
            Path directorioPath = Paths.get(rutaImagenes);
            if (!Files.exists(directorioPath)) {
                Files.createDirectories(directorioPath);
            }

            // Eliminar archivos antiguos
            if (vehiculo.getImagenes() != null) {
                for (Imagen img : vehiculo.getImagenes()) {
                    try {
                        Files.deleteIfExists(directorioPath.resolve(img.getNombre()));
                    } catch (IOException e) {
                        System.err.println("No se pudo eliminar la imagen: " + img.getNombre());
                    }
                }
                vehiculo.getImagenes().clear();
            }

            List<String> extensionesPermitidas = List.of("jpg", "jpeg", "png", "gif");

            for (MultipartFile archivo : imagenes) {
                String extension = FilenameUtils.getExtension(archivo.getOriginalFilename()).toLowerCase();
                if (!extensionesPermitidas.contains(extension)) {
                    return new ResponseEntity<>(new ApiResponse<>("Formato de imagen no permitido: " + extension, null, false),
                            HttpStatus.BAD_REQUEST);
                }

                String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                Path rutaImagen = directorioPath.resolve(nombre);
                Files.write(rutaImagen, archivo.getBytes());

                Imagen imagen = new Imagen();
                imagen.setNombre(nombre);
                imagen.setVehiculo(vehiculo);
                vehiculo.getImagenes().add(imagen);
            }

            VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculoService.guardar(vehiculo));
            return new ResponseEntity<>(new ApiResponse<>("Imágenes subidas correctamente", dto, true), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse<>("Error al subir imágenes: " + e.getMessage(), null, false),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Integer id, @RequestBody VehiculoPutDTO vehiculoDTO) {
        try {
            Vehiculo vehiculo = vehiculoService.obtener(id).orElse(null);
            if (vehiculo != null) {
                vehiculo.setActivo(vehiculoDTO.getActivo());
                vehiculo.setAnioModelo(vehiculoDTO.getAnioModelo());
                vehiculo.setColor(vehiculoDTO.getColor());
                vehiculo.setEstado(vehiculoDTO.getEstado());
                vehiculo.setKilometraje(vehiculoDTO.getKilometraje());
                vehiculo.setMarca(vehiculoDTO.getMarca());
                vehiculo.setModelo(vehiculoDTO.getModelo());
                vehiculo.setPrecio(vehiculoDTO.getPrecio());
                vehiculo.setStock(vehiculoDTO.getStock());
                vehiculo.setTipo(vehiculoDTO.getTipo());

                List<DetalleVenta> detalle = detalleService.obtenerById(vehiculoDTO.getDetalleVentasId());
                if (detalle.size() != vehiculoDTO.getDetalleVentasId().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Una o más detalles no existen", null, false), HttpStatus.BAD_REQUEST);
                }
                vehiculo.setDetalleVentas(detalle);

                VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculoService.guardar(vehiculo));
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo actualizado con existo", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo no encontrado", vehiculoDTO, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable Integer id) {
        try {
            Vehiculo vehiculo = vehiculoService.obtener(id).orElse(null);
            if (vehiculo != null) {
                vehiculoService.eliminar(vehiculo.getId());
                VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculo);
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo eliminado con existo", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Vehiculo no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{idVehiculo}/imagen/{idImagen}")
    public ResponseEntity<?> eliminarImagenVehiculo(@PathVariable Integer idVehiculo,
                                                    @PathVariable Integer idImagen) {
        try {
            Optional<Vehiculo> optionalVehiculo = vehiculoService.obtener(idVehiculo);
            if (optionalVehiculo.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>("Vehículo no encontrado", null, false), HttpStatus.NOT_FOUND);
            }

            Vehiculo vehiculo = optionalVehiculo.get();

            // Buscar la imagen dentro del vehículo
            Imagen imagen = vehiculo.getImagenes().stream()
                    .filter(img -> img.getId().equals(idImagen))
                    .findFirst()
                    .orElse(null);

            if (imagen == null) {
                return new ResponseEntity<>(new ApiResponse<>("Imagen no encontrada en este vehículo", null, false), HttpStatus.NOT_FOUND);
            }

            // Eliminar físicamente el archivo
            Path ruta = Paths.get(rutaImagenes).resolve(imagen.getNombre()).toAbsolutePath();
            try {
                Files.deleteIfExists(ruta);
            } catch (IOException e) {
                return new ResponseEntity<>(new ApiResponse<>("Error al eliminar archivo físico: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Eliminar de la relación con el vehículo
            vehiculo.getImagenes().remove(imagen);

            vehiculoService.guardar(vehiculo);

            VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculo);
            return new ResponseEntity<>(new ApiResponse<>("Imagen eliminada con éxito", dto, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

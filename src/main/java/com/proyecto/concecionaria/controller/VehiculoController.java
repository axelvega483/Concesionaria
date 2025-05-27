package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoGetDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoMapper;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPostDTO;
import com.proyecto.concecionaria.entity.DetalleVenta;
import com.proyecto.concecionaria.entity.Imagen;
import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.service.DetalleVentaService;
import com.proyecto.concecionaria.service.ImagenService;
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
    private DetalleVentaService detalleService;

    @Autowired
    private ImagenService imagenService;

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

    @GetMapping("/imagen/{nombre}")
    public ResponseEntity<Resource> verImagen(@PathVariable String nombre) {
        try {
            Path ruta = Paths.get(rutaImagenes).resolve(nombre).toAbsolutePath();
            Resource recurso = new UrlResource(ruta.toUri());

            if (!recurso.exists() || !recurso.isReadable()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String extension = FilenameUtils.getExtension(nombre).toLowerCase();

            MediaType contentType = switch (extension) {
                case "png" ->
                    MediaType.IMAGE_PNG;
                case "jpg", "jpeg" ->
                    MediaType.IMAGE_JPEG;
                case "gif" ->
                    MediaType.IMAGE_GIF;
                default ->
                    MediaType.APPLICATION_OCTET_STREAM;
            };

            return ResponseEntity.ok().contentType(contentType).body(recurso);

        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

    @PostMapping
    public ResponseEntity<?> crearVehiculo(@Valid @RequestBody VehiculoPostDTO vehiculoDTO) {
        try {
            if (vehiculoService.existe(vehiculoDTO.getMarca(), vehiculoDTO.getModelo(), vehiculoDTO.getAnioModelo())) {
                Vehiculo vehiculo = vehiculoService.obtener(vehiculoDTO.getId()).orElse(null);
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
                    imagen.setActivo(true);
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

            for (Imagen img : vehiculo.getImagenes()) {
                imagenService.eliminar(img.getId());
                Files.deleteIfExists(directorioPath.resolve(img.getNombre()));
            }
            vehiculo.getImagenes().clear();

            List<String> extensionesPermitidas = List.of("jpg", "jpeg", "png", "gif");
            for (MultipartFile archivo : imagenes) {
                String extension = FilenameUtils.getExtension(archivo.getOriginalFilename()).toLowerCase();

                if (!extensionesPermitidas.contains(extension)) {
                    return new ResponseEntity<>(new ApiResponse<>("Formato de imagen no permitido: " + extension, null, false), HttpStatus.BAD_REQUEST);
                }

                String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
                Path rutaImagen = directorioPath.resolve(nombre);
                Files.write(rutaImagen, archivo.getBytes());

                Imagen imagen = new Imagen();
                imagen.setActivo(true);
                imagen.setNombre(nombre);
                vehiculo.addImagen(imagen);
            }

            vehiculoService.guardar(vehiculo);
            VehiculoGetDTO dto = VehiculoMapper.toDTO(vehiculo);
            return new ResponseEntity<>(new ApiResponse<>("Imágenes subidas correctamente", dto, true), HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse<>("Error al subir imágenes: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoGetDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPostDTO;
import com.proyecto.concecionaria.DTOs.Vehiculo.VehiculoPutDTO;
import com.proyecto.concecionaria.entity.Imagen;
import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.interfaz.ImagenIntefaz;
import com.proyecto.concecionaria.interfaz.VehiculoInterfaz;
import com.proyecto.concecionaria.util.CustomApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Vehiculo", description = "Controlador para operaciones de vehículos")
public class VehiculoController {

    @Autowired
    private VehiculoInterfaz vehiculoService;
    @Autowired
    private ImagenIntefaz imagenService;

    @Value("${app.ruta.imagenes}")
    private String rutaImagenes;


    @Operation(summary = "Listar todos los vehículos", description = "Obtiene una lista de todos los vehículos activos en el sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vehículos listados correctamente"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<?> listarVehiculos() {
        List<VehiculoGetDTO> dto = vehiculoService.listar();
        return new ResponseEntity<>(new CustomApiResponse<>("Listado de vehículos obtenido correctamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener vehículo por ID", description = "Busca un vehículo específico por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vehículo encontrado exitosamente"), @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerVehiculo(@Parameter(description = "ID del vehículo a buscar", example = "1", required = true) @PathVariable Integer id) {
        Optional<VehiculoGetDTO> dto = vehiculoService.obtener(id);
        if (dto.isPresent()) {
            return new ResponseEntity<>(new CustomApiResponse<>("Vehículo encontrado", dto.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Vehículo no encontrado", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Ver imagen de vehículo", description = "Devuelve la imagen de un vehículo por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Imagen encontrada", content = @Content(mediaType = "image/*")), @ApiResponse(responseCode = "404", description = "Imagen no encontrada"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("/imagen/{id}")
    public ResponseEntity<Resource> verImagen(@Parameter(description = "ID de la imagen a visualizar", example = "1", required = true) @PathVariable Integer id) throws MalformedURLException {
        Imagen imagen = imagenService.obtener(id).orElse(null);
        if (imagen == null) {
            return ResponseEntity.notFound().build();
        }

        Path ruta = Paths.get(rutaImagenes).resolve(imagen.getNombre()).toAbsolutePath();
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

        return ResponseEntity.ok().contentType(mediaType).body(recurso);
    }

    @Operation(summary = "Crear nuevo vehículo", description = "Crea un nuevo vehículo en el sistema. Si ya existe uno con misma marca, modelo y año, actualiza el stock")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vehículo creado o stock actualizado exitosamente"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping
    public ResponseEntity<?> crearVehiculo(@Parameter(description = "Datos del vehículo a crear", required = true) @Valid @RequestBody VehiculoPostDTO vehiculoDTO) {
        VehiculoGetDTO dto = vehiculoService.crear(vehiculoDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Vehículo procesado exitosamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Subir imágenes para vehículo", description = "Sube una o múltiples imágenes para un vehículo específico")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Imágenes subidas correctamente"), @ApiResponse(responseCode = "400", description = "Formato de imagen no permitido"), @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping(value = "subir-imagen/{idVehiculo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirImagenesVehiculo(@Parameter(description = "ID del vehículo al que se asociarán las imágenes", example = "1", required = true) @PathVariable Integer idVehiculo, @Parameter(description = "Archivos de imagen a subir (formatos permitidos: jpg, jpeg, png, gif)", required = true) @RequestParam("imagenes") MultipartFile[] imagenes) throws IOException {

        Optional<Vehiculo> optional = vehiculoService.obtenerEntidad(idVehiculo);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(new CustomApiResponse<>("Vehículo no encontrado", null, false), HttpStatus.NOT_FOUND);
        }

        Vehiculo vehiculo = optional.get();
        Path directorioPath = Paths.get(rutaImagenes);
        if (!Files.exists(directorioPath)) {
            Files.createDirectories(directorioPath);
        }

        // Eliminar archivos antiguos (si quieres reemplazar todas las imágenes)
        if (vehiculo.getImagenes() != null) {
            for (Imagen img : vehiculo.getImagenes()) {
                Files.deleteIfExists(directorioPath.resolve(img.getNombre()));
            }
            vehiculo.getImagenes().clear();
        }

        List<String> extensionesPermitidas = List.of("jpg", "jpeg", "png", "gif");

        for (MultipartFile archivo : imagenes) {
            String extension = FilenameUtils.getExtension(archivo.getOriginalFilename()).toLowerCase();
            if (!extensionesPermitidas.contains(extension)) {
                return new ResponseEntity<>(new CustomApiResponse<>("Formato de imagen no permitido: " + extension, null, false), HttpStatus.BAD_REQUEST);
            }

            String nombre = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
            Path rutaImagen = directorioPath.resolve(nombre);
            Files.write(rutaImagen, archivo.getBytes());

            Imagen imagen = new Imagen();
            imagen.setNombre(nombre);
            imagen.setVehiculo(vehiculo);
            vehiculo.getImagenes().add(imagen);
        }

        VehiculoGetDTO dto = vehiculoService.guardar(vehiculo);
        return new ResponseEntity<>(new CustomApiResponse<>("Imágenes subidas correctamente", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar vehículo", description = "Actualiza la información de un vehículo existente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"), @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarVehiculo(@Parameter(description = "ID del vehículo a actualizar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos actualizados del vehículo", required = true) @RequestBody VehiculoPutDTO vehiculoDTO) {
        VehiculoGetDTO dto = vehiculoService.actualizar(id, vehiculoDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Vehículo actualizado exitosamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar vehículo", description = "Elimina (desactiva) un vehículo del sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Vehículo eliminado exitosamente"), @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarVehiculo(@Parameter(description = "ID del vehículo a eliminar", example = "1", required = true) @PathVariable Integer id) {
        VehiculoGetDTO dto = vehiculoService.eliminar(id);
        return new ResponseEntity<>(new CustomApiResponse<>("Vehículo eliminado exitosamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar imagen de vehículo", description = "Elimina una imagen específica de un vehículo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Imagen eliminada exitosamente"), @ApiResponse(responseCode = "404", description = "Vehículo o imagen no encontrada"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("{idVehiculo}/imagen/{idImagen}")
    public ResponseEntity<?> eliminarImagenVehiculo(@Parameter(description = "ID del vehículo", example = "1", required = true) @PathVariable Integer idVehiculo, @Parameter(description = "ID de la imagen a eliminar", example = "1", required = true) @PathVariable Integer idImagen) throws IOException {

        Optional<Vehiculo> optionalVehiculo = vehiculoService.obtenerEntidad(idVehiculo);
        if (optionalVehiculo.isEmpty()) {
            return new ResponseEntity<>(new CustomApiResponse<>("Vehículo no encontrado", null, false), HttpStatus.NOT_FOUND);
        }

        Vehiculo vehiculo = optionalVehiculo.get();
        Imagen imagen = vehiculo.getImagenes().stream().filter(img -> img.getId().equals(idImagen)).findFirst().orElse(null);

        if (imagen == null) {
            return new ResponseEntity<>(new CustomApiResponse<>("Imagen no encontrada en este vehículo", null, false), HttpStatus.NOT_FOUND);
        }

        // Eliminar físicamente el archivo
        Path ruta = Paths.get(rutaImagenes).resolve(imagen.getNombre()).toAbsolutePath();
        Files.deleteIfExists(ruta);

        // Eliminar de la relación con el vehículo
        vehiculo.getImagenes().remove(imagen);
        VehiculoGetDTO dto = vehiculoService.guardar(vehiculo);

        return new ResponseEntity<>(new CustomApiResponse<>("Imagen eliminada con éxito", dto, true), HttpStatus.OK);
    }

}

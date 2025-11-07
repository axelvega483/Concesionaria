package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Venta.VentaGetDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaPostDTO;
import com.proyecto.concecionaria.interfaz.VentaInterfaz;
import com.proyecto.concecionaria.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("ventas")
public class VentaController {

    @Autowired
    private VentaInterfaz ventaService;


    @Operation(summary = "Listar todas las ventas", description = "Obtiene una lista de todas las ventas activas en el sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ventas listadas correctamente"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<?> listarVentas() {
        List<VentaGetDTO> dto = ventaService.listar();
        return new ResponseEntity<>(new CustomApiResponse<>("Listado de ventas obtenido correctamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener venta por ID", description = "Busca una venta específica por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"), @ApiResponse(responseCode = "404", description = "Venta no encontrada"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerVenta(@Parameter(description = "ID de la venta a buscar", example = "1", required = true) @PathVariable Integer id) {
        Optional<VentaGetDTO> dto = ventaService.obtener(id);
        if (dto.isPresent()) {
            return new ResponseEntity<>(new CustomApiResponse<>("Venta encontrada", dto.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Venta no encontrada", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear nueva venta", description = "Registra una nueva venta en el sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Venta creada exitosamente"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o stock insuficiente"), @ApiResponse(responseCode = "404", description = "Cliente o empleado no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping
    public ResponseEntity<?> crearVenta(@Parameter(description = "Datos de la venta a crear", required = true) @Valid @RequestBody VentaPostDTO ventaDTO) {
        VentaGetDTO dto = ventaService.crear(ventaDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Venta creada exitosamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar venta", description = "Elimina (desactiva) una venta del sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Venta eliminada exitosamente"), @ApiResponse(responseCode = "404", description = "Venta no encontrada"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarVenta(@Parameter(description = "ID de la venta a eliminar", example = "1", required = true) @PathVariable Integer id) {
        VentaGetDTO dto = ventaService.cancelar(id);
        return new ResponseEntity<>(new CustomApiResponse<>("Venta dada de baja exitosamente", dto, true), HttpStatus.OK);
    }
}
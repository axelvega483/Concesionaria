package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Cliente.ClienteGetDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePostDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePutDTO;
import com.proyecto.concecionaria.interfaz.ClienteInterfaz;
import com.proyecto.concecionaria.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteInterfaz clienteService;

    @Operation(summary = "Listar todos los clientes", description = "Devuelve una lista con todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> listarCliente() {
        List<ClienteGetDTO> dto = clienteService.listar();
        return new ResponseEntity<>(new CustomApiResponse<>("Lista de Clientes", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Devuelve un cliente específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerCliente(
            @Parameter(description = "ID del cliente a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        ClienteGetDTO dto = clienteService.obtener(id).orElse(null);
        if (dto != null) {
            return new ResponseEntity<>(new CustomApiResponse<>("Cliente encontrado", dto, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> crearCliente(
            @Parameter(description = "Datos del cliente a crear", required = true)
            @Valid @RequestBody ClientePostDTO clienteDTO) {
        ClienteGetDTO dto = clienteService.crear(clienteDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Cliente creado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar cliente existente", description = "Actualiza la información de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarCliente(
            @Parameter(description = "ID del cliente a actualizar", example = "1", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del cliente", required = true)
            @RequestBody ClientePutDTO clienteDTO) {
        ClienteGetDTO dto = clienteService.actualizar(id, clienteDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Cliente actualizado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarCliente(
            @Parameter(description = "ID del cliente a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        ClienteGetDTO dto = clienteService.eliminar(id);
        return new ResponseEntity<>(new CustomApiResponse<>("Cliente eliminado", dto, true), HttpStatus.OK);
    }
}

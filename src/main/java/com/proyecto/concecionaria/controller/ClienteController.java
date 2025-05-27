package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Cliente.ClienteGetDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClienteMapper;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePostDTO;
import com.proyecto.concecionaria.DTOs.Cliente.ClientePutDTO;
import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.service.ClienteService;
import com.proyecto.concecionaria.service.VentaService;
import com.proyecto.concecionaria.util.ApiResponse;
import jakarta.validation.Valid;
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
    private ClienteService clienteService;
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<?> listarCliente() {
        try {
            List<ClienteGetDTO> dto = clienteService.listar()
                    .stream()
                    .map(ClienteMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Lista de Clientes", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.obtener(id).orElse(null);
            if (cliente != null) {
                ClienteGetDTO dto = ClienteMapper.toDTO(cliente);
                return new ResponseEntity<>(new ApiResponse<>("Cliente encontrado ", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado ", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody ClientePostDTO clienteDTO) {
        try {
            if (clienteService.existe(clienteDTO.getDni())) {
                return new ResponseEntity<>(new ApiResponse<>("Error!! El cliente ya existe", clienteDTO, false), HttpStatus.CONFLICT);
            }
            List<Venta> ventas = ventaService.obtenerById(clienteDTO.getVentasId());
            if (ventas.size() != clienteDTO.getVentasId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Una o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
            }

            Cliente cliente = new Cliente();
            cliente.setActivo(Boolean.TRUE);
            cliente.setDni(clienteDTO.getDni());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setVentas(ventas);
            ClienteGetDTO dto = ClienteMapper.toDTO(clienteService.guardar(cliente));
            return new ResponseEntity<>(new ApiResponse<>("Cliente creado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> ActualizarCliente(@PathVariable Integer id, @RequestBody ClientePutDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.obtener(id).orElse(null);
            if (cliente != null) {
                List<Venta> ventas = ventaService.obtenerById(clienteDTO.getVentasId());
                if (ventas.size() != clienteDTO.getVentasId().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Una o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
                }

                cliente.setActivo(Boolean.TRUE);
                cliente.setDni(clienteDTO.getDni());
                cliente.setEmail(clienteDTO.getEmail());
                cliente.setNombre(clienteDTO.getNombre());
                cliente.setVentas(ventas);
                ClienteGetDTO dto = ClienteMapper.toDTO(clienteService.guardar(cliente));
                return new ResponseEntity<>(new ApiResponse<>("Cliente actualizado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.obtener(id).orElse(null);
            if (cliente != null) {
                clienteService.eliminar(cliente.getId());
                ClienteGetDTO dto = ClienteMapper.toDTO(cliente);
                return new ResponseEntity<>(new ApiResponse<>("Cliente eliminado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

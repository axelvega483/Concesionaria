package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Pagos.PagosGetDTO;
import com.proyecto.concecionaria.DTOs.Pagos.PagosMapper;
import com.proyecto.concecionaria.DTOs.Pagos.PagosPutDTO;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.service.PagosService;
import com.proyecto.concecionaria.util.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping
    public ResponseEntity<?> listarPagos() {
        try {
            List<PagosGetDTO> dto = pagosService.listar()
                    .stream()
                    .map(PagosMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Listado de pagos", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerPago(@PathVariable Integer id) {
        try {
            Pagos pagos = pagosService.obtener(id).orElse(null);
            if (pagos != null) {
                PagosGetDTO dto = PagosMapper.toDTO(pagos);
                return new ResponseEntity<>(new ApiResponse<>("Pago encontrado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Pago no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/confirmar/{id}")
    public ResponseEntity<?> confirmarPago(@PathVariable Integer id, @RequestBody PagosPutDTO putDTO) {
        try {
            Pagos pagos = pagosService.confirmarPago(id, putDTO.getMetodoPago());
            PagosGetDTO dto = PagosMapper.toDTO(pagos);
            return new ResponseEntity<>(new ApiResponse<>("Pago realizado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> cancelarPago(@PathVariable Integer id) {
        try {
            pagosService.cancelarPago(id);
            return new ResponseEntity<>(new ApiResponse<>("Pago anulado", null, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

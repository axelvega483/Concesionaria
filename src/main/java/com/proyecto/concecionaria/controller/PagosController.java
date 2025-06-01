package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Pagos.PagosGetDTO;
import com.proyecto.concecionaria.DTOs.Pagos.PagosMapper;
import com.proyecto.concecionaria.DTOs.Pagos.PagosPutDTO;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.service.PagosService;
import com.proyecto.concecionaria.util.ApiResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Value("${ruta.pdf}")
    private String RUTA_PDF;

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

    @GetMapping("/ticket/{id}")
    public ResponseEntity<InputStreamResource> downloadPDF(@PathVariable Integer id) throws FileNotFoundException {
        Optional<Pagos> optionalPago = pagosService.obtener(id);
        if (optionalPago.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pagos pago = optionalPago.get();
        String fileName = "ticket-pago-" + pago.getId() + ".pdf";
        String filePath = RUTA_PDF + File.separator + fileName;

        File archivo = new File(filePath);
        if (!archivo.exists()) {
            pagosService.generarTicketPago(pago);
        }

        FileInputStream fileInputStream = new FileInputStream(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(fileInputStream));
    }

}

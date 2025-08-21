package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Pagos.PagosGetDTO;
import com.proyecto.concecionaria.DTOs.Pagos.PagosMapper;
import com.proyecto.concecionaria.DTOs.Pagos.PagosPutDTO;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.service.PagosServices;
import com.proyecto.concecionaria.service.PdfPagoService;
import com.proyecto.concecionaria.util.ApiResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("pagos")
public class PagosController {

    @Autowired
    private PagosServices pagosService;

    @Autowired
    private PdfPagoService pdf;

    @Value("${app.ruta.PDF}")
    private String RUTA_PDF;

    @GetMapping
    public ResponseEntity<?> listarPagos() {
        try {
            List<PagosGetDTO> dto = pagosService.findByAll()
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
            Pagos pagos = pagosService.findById(id).orElse(null);
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
            Pagos pagos = pagosService.findById(id).orElse(null);
            if (pagos != null) {
                pagos.setFechaPago(LocalDate.now());
                pagos.setMetodoPago(putDTO.getMetodoPago());
                pagos.confirmarPago();
                pagos = pagosService.save(pagos);
                PagosGetDTO dto = PagosMapper.toDTO(pagos);
                return new ResponseEntity<>(new ApiResponse<>("Pago realizado", dto, true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>("Pago no encontrado",null,false),HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> cancelarPago(@PathVariable Integer id) {
        try {
            pagosService.cancelar(id);
            return new ResponseEntity<>(new ApiResponse<>("Pago anulado", null, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<InputStreamResource> downloadPDF(@PathVariable Integer id) throws FileNotFoundException {
        Optional<Pagos> optionalPago = pagosService.findById(id);
        if (optionalPago.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pagos pago = optionalPago.get();
        String fileName = "ticket-pago-" + pago.getId() + ".pdf";
        String filePath = RUTA_PDF + File.separator + fileName;
        File archivo = new File(filePath);

        // Genera PDF si no existe
        if (!archivo.exists()) {
            String generado = pdf.generarTicketPagoPDF(pago);
            if (generado == null || !new File(generado).exists()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        FileInputStream fis = new FileInputStream(filePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(fis));
    }


}

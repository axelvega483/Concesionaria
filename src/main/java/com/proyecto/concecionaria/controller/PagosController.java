package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.Pagos.PagosGetDTO;
import com.proyecto.concecionaria.DTOs.Pagos.PagosPutDTO;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.service.PdfPagoService;
import com.proyecto.concecionaria.util.CustomApiResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private PagosInterfaz pagosService;

    @Autowired
    private PdfPagoService pdf;

    @Value("${app.ruta.PDF}")
    private String RUTA_PDF;

    @Operation(summary = "Listar todos los pagos", description = "Obtiene una lista de todos los pagos activos del sistema")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pagos listados correctamente"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<?> listarPagos() {
        List<PagosGetDTO> dto = pagosService.findByAll();
        return new ResponseEntity<>(new CustomApiResponse<>("Listado de pagos obtenido correctamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener pago por ID", description = "Busca un pago específico por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pago encontrado exitosamente"), @ApiResponse(responseCode = "404", description = "Pago no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("{id}")
    public ResponseEntity<?> obtenerPago(@Parameter(description = "ID del pago a buscar", example = "1", required = true) @PathVariable Integer id) {
        Optional<PagosGetDTO> dto = pagosService.findById(id);
        if (dto.isPresent()) {
            return new ResponseEntity<>(new CustomApiResponse<>("Pago encontrado", dto.get(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Pago no encontrado", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Confirmar pago", description = "Confirma un pago pendiente y actualiza su estado a PAGADO")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pago confirmado exitosamente"), @ApiResponse(responseCode = "404", description = "Pago no encontrado"), @ApiResponse(responseCode = "400", description = "Datos de pago inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("/confirmar/{id}")
    public ResponseEntity<?> confirmarPago(@Parameter(description = "ID del pago a confirmar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos de confirmación del pago", required = true) @RequestBody PagosPutDTO putDTO) {
        PagosGetDTO dto = pagosService.confirmarPago(id, putDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Pago confirmado exitosamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Cancelar pago", description = "Cancela un pago existente y lo marca como inactivo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pago cancelado exitosamente"), @ApiResponse(responseCode = "404", description = "Pago no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("{id}")
    public ResponseEntity<?> cancelarPago(@Parameter(description = "ID del pago a cancelar", example = "1", required = true) @PathVariable Integer id) {
        PagosGetDTO dto = pagosService.cancelar(id);
        return new ResponseEntity<>(new CustomApiResponse<>("Pago cancelado exitosamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Descargar ticket de pago", description = "Genera y descarga el ticket de pago en formato PDF")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ticket PDF generado correctamente", content = @Content(mediaType = "application/pdf")), @ApiResponse(responseCode = "404", description = "Pago no encontrado"), @ApiResponse(responseCode = "500", description = "Error al generar el PDF")})
    @GetMapping("/ticket/{id}")
    public ResponseEntity<InputStreamResource> downloadPDF(@Parameter(description = "ID del pago para generar el ticket", example = "1", required = true) @PathVariable Integer id) throws FileNotFoundException {

        Optional<Pagos> optionalPago = pagosService.findEntityById(id);
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
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"").contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(fis));
    }
}

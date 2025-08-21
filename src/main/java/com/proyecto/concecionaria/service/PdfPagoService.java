package com.proyecto.concecionaria.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.util.EstadoPagos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.function.BiConsumer;

@Service
public class PdfPagoService {

    @Value("${app.ruta.PDF}")
    private String rutaPdf;

    public String generarTicketPagoPDF(Pagos pago) {
        try {
            File directory = new File(rutaPdf);
            if (!directory.exists() && !directory.mkdirs()) {
                System.err.println("No se pudo crear la carpeta: " + rutaPdf);
                return null;
            }

            int cuota = 0;
            for (Pagos p : pago.getVenta().getPagos()) {
                cuota++;
                if (p.getEstado().equals(EstadoPagos.PENDIENTE)) break;
            }

            String pathArchivo = directory + File.separator + "ticket-pago-" + pago.getId() + ".pdf";
            Document document = new Document(new Rectangle(220, 400), 10, 10, 10, 10);
            PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));
            document.open();

            // Fuentes
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLDITALIC);
            Font fontCampo = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            Font fontValor = new Font(Font.FontFamily.HELVETICA, 9);
            Font fontLinea = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC);

            // Encabezado
            Paragraph titulo = new Paragraph("🏢 Concesionaria", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(8f);
            document.add(titulo);

            // Fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaStr = pago.getFechaPago() != null
                    ? pago.getFechaPago().format(formatter)
                    : EstadoPagos.PENDIENTE.name();
            Paragraph fecha = new Paragraph(fechaStr, fontValor);
            fecha.setAlignment(Element.ALIGN_CENTER);
            fecha.setSpacingAfter(10f);
            document.add(fecha);

            // Separador
            document.add(new Paragraph("────────────────────────────", fontLinea));

            // Datos en tabla
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidths(new int[]{1, 2});
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(5f);
            tabla.setSpacingAfter(5f);

            // Helper para agregar fila
            BiConsumer<String, String> addFila = (campo, valor) -> {
                PdfPCell cellCampo = new PdfPCell(new Phrase(campo, fontCampo));
                cellCampo.setBorder(Rectangle.NO_BORDER);
                PdfPCell cellValor = new PdfPCell(new Phrase(valor, fontValor));
                cellValor.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(cellCampo);
                tabla.addCell(cellValor);
            };

            addFila.accept("📄 ID Pago:", pago.getId().toString());
            addFila.accept("👤 Cliente:", pago.getVenta().getCliente().getNombre());
            addFila.accept("🧑‍💼 Agente:", pago.getVenta().getEmpleado().getNombre());
            addFila.accept("📑 Contrato:", pago.getVenta().getId().toString());
            addFila.accept("📆 Cuota:", (cuota - 1) + " de " + pago.getVenta().getPagos().size());
            addFila.accept("💵 Monto:", "$" + String.format("%.2f", pago.getMonto()));
            addFila.accept("💳 Método:", pago.getMetodoPago().name());

            document.add(tabla);

            // Separador final
            document.add(new Paragraph("────────────────────────────", fontLinea));

            // Mensaje de cierre
            Paragraph gracias = new Paragraph("¡Gracias por su pago!", fontSubtitulo);
            gracias.setAlignment(Element.ALIGN_CENTER);
            gracias.setSpacingBefore(8f);
            document.add(gracias);

            document.close();
            return pathArchivo;

        } catch (DocumentException | IOException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            return null;
        }
    }

}

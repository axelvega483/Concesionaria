package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.interfaz.PagosInterfaz;
import com.proyecto.concecionaria.repository.PagosRepository;
import com.proyecto.concecionaria.util.EstadoPagos;
import com.itextpdf.text.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagosService implements PagosInterfaz {

    @Autowired
    private PagosRepository repo;
    @Autowired
    private VentaService ventaService;

    @Value("${ruta.pdf}")
    private String RUTA_PDF;

    @Override
    public Pagos guardar(Pagos pagos) {
        return repo.save(pagos);
    }

    @Override
    public Optional<Pagos> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Pagos> listar() {
        return repo.findAllActivo();
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(pagos -> {
            pagos.setActivo(Boolean.FALSE);
            repo.save(pagos);
        });
    }

    public List<Pagos> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }

    public Pagos confirmarPago(Integer id, String metodoPago) {
        Pagos pagos = obtener(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        if (pagos.getEstado() == EstadoPagos.PAGADO) {
            throw new IllegalStateException("El pago ya se encuentra realizado");
        }
        pagos.setMetodoPago(metodoPago);
        pagos.confirmarPago();
        generarTicketPago(pagos);
        pagos = guardar(pagos);
        ventaService.guardar(pagos.getVenta());

        return pagos;
    }

    public Pagos cancelarPago(Integer id) {
        Pagos pago = obtener(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        if (pago.getEstado() == EstadoPagos.PAGADO) {
            throw new IllegalStateException("No se puede anular un pago ya confirmado");
        }

        pago.setEstado(EstadoPagos.PENDIENTE);
        pago.setFechaPago(null);
        pago.setMetodoPago(null);
        pago.setActivo(true);
        pago = guardar(pago);
        pago.getVenta().actualizarSaldo();
        ventaService.guardar(pago.getVenta());

        return pago;
    }

    public void generarTicketPago(Pagos pagoBD) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            File directory = new File(RUTA_PDF);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            int cuota = 0;
            for (Pagos pago : pagoBD.getVenta().getPagos()) {
                cuota++;
                if (pago.getEstado().equals(EstadoPagos.PENDIENTE)) {
                    break;
                }
            }

            String pathArchivo = directory + File.separator + "ticket-pago-" + pagoBD.getId() + ".pdf";

            Document document = new Document(new Rectangle(220, 400));
            PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));
            document.open();

            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font fontCuerpo = new Font(Font.FontFamily.HELVETICA, 9);
            Font fontLinea = new Font(Font.FontFamily.HELVETICA, 8);

            Paragraph titulo = new Paragraph("REAL STATE | NOA", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph fecha = new Paragraph(pagoBD.getFechaPago().format(formatter), fontCuerpo);
            fecha.setAlignment(Element.ALIGN_CENTER);
            document.add(fecha);

            document.add(new Paragraph("--------------------------------------------------", fontLinea));

            document.add(new Paragraph("📄 ID de Pago: " + pagoBD.getId(), fontCuerpo));
            document.add(new Paragraph("👤 Cliente: " + pagoBD.getVenta().getCliente().getNombre(), fontCuerpo));
            document.add(new Paragraph("🧑‍💼 Agente: " + pagoBD.getVenta().getEmpleado().getNombre(), fontCuerpo));
            document.add(new Paragraph("📑 Contrato: " + pagoBD.getVenta().getId(), fontCuerpo));
            document.add(new Paragraph("📆 Cuota: " + (cuota - 1) + " de " + pagoBD.getVenta().getPagos().size(), fontCuerpo));
            document.add(new Paragraph("💵 Monto: $" + pagoBD.getMonto(), fontCuerpo));
            document.add(new Paragraph("💳 Método de Pago: " + pagoBD.getMetodoPago(), fontCuerpo));

            document.add(new Paragraph("--------------------------------------------------", fontLinea));

            Paragraph gracias = new Paragraph("¡Gracias por su pago!", fontSubtitulo);
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}

package com.proyecto.concecionaria.DTOs.Venta;

import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record VentaGetDTO (
         Integer id,
         LocalDate fecha,
         FrecuenciaPago frecuenciaPago,
         BigDecimal total,
         List<VentaDetalleDTO> detalleVentas,
         Cliente cliente,
         Usuario empleado,
         List<VentaPagosDTO> pagos,
         Boolean activo,
         Double entrega,
         EstadoVenta estado,
         Integer cuotas){


}

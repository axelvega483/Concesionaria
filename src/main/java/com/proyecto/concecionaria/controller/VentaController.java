package com.proyecto.concecionaria.controller;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleMapper;
import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaGetDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaMapper;
import com.proyecto.concecionaria.DTOs.Venta.VentaPostDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaPutDTO;
import com.proyecto.concecionaria.entity.Cliente;
import com.proyecto.concecionaria.entity.DetalleVenta;
import com.proyecto.concecionaria.entity.Pagos;
import com.proyecto.concecionaria.entity.Usuario;
import com.proyecto.concecionaria.entity.Vehiculo;
import com.proyecto.concecionaria.entity.Venta;
import com.proyecto.concecionaria.service.ClienteService;
import com.proyecto.concecionaria.service.DetalleVentaService;
import com.proyecto.concecionaria.service.PagosService;
import com.proyecto.concecionaria.service.UsuarioService;
import com.proyecto.concecionaria.service.VehiculoService;
import com.proyecto.concecionaria.service.VentaService;
import com.proyecto.concecionaria.util.ApiResponse;
import jakarta.validation.Valid;
import java.lang.annotation.Retention;
import java.util.ArrayList;
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
@RequestMapping("ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<?> listarVentas() {
        try {
            List<VentaGetDTO> dto = ventaService.listar()
                    .stream()
                    .map(VentaMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Listado de ventas", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerVenta(@PathVariable Integer id) {
        try {
            Venta venta = ventaService.obtener(id).orElse(null);
            if (venta != null) {
                VentaGetDTO dto = VentaMapper.toDTO(venta);
                return new ResponseEntity<>(new ApiResponse<>("Venta encontrada", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Venta no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearVenta(@Valid @RequestBody VentaPostDTO ventaDTO) {
        try {
            Usuario usuario = usuarioService.obtener(ventaDTO.getEmpleado().getId()).orElse(null);
            if (usuario == null) {
                return new ResponseEntity<>(new ApiResponse<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
            Cliente cliente = clienteService.obtener(ventaDTO.getCliente().getId()).orElse(null);
            if (cliente == null) {
                return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
            }

            Venta venta = new Venta();
            venta.setActivo(ventaDTO.getActivo());
            venta.setCliente(cliente);
            venta.setCuotas(ventaDTO.getCuotas());
            venta.setEmpleado(usuario);
            venta.setEntrega(ventaDTO.getEntrega());
            venta.setEstado(ventaDTO.getEstado());
            venta.setFecha(ventaDTO.getFecha());
            venta.setFrecuenciaPago(ventaDTO.getFrecuenciaPago());
            venta.setTotal(ventaDTO.getTotal());

            venta.setDetalleVentas(mapearDetalles(ventaDTO.getDetalleVentas(), venta));

            venta.generarPagos();
            VentaGetDTO dto = VentaMapper.toDTO(ventaService.guardar(venta));
            return new ResponseEntity<>(new ApiResponse<>("Venta creada ", dto, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer id, @RequestBody VentaPutDTO ventaDTO) {
        try {
            Venta venta = ventaService.obtener(id).orElse(null);
            if (venta != null) {
                Usuario usuario = usuarioService.obtener(ventaDTO.getEmpleado().getId()).orElse(null);
                if (usuario == null) {
                    return new ResponseEntity<>(new ApiResponse<>("Usuario no encontrado", null, false), HttpStatus.NOT_FOUND);
                }
                Cliente cliente = clienteService.obtener(ventaDTO.getCliente().getId()).orElse(null);
                if (cliente == null) {
                    return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
                }

                venta.setActivo(ventaDTO.getActivo());
                venta.setCliente(cliente);
                venta.setCuotas(ventaDTO.getCuotas());
                venta.setEmpleado(usuario);
                venta.setEntrega(ventaDTO.getEntrega());
                venta.setEstado(ventaDTO.getEstado());
                venta.setFecha(ventaDTO.getFecha());
                venta.setFrecuenciaPago(ventaDTO.getFrecuenciaPago());
                venta.setTotal(ventaDTO.getTotal());

                venta.setDetalleVentas(mapearDetalles(ventaDTO.getDetalleVentas(), venta));

                venta.generarPagos();
                VentaGetDTO dto = VentaMapper.toDTO(ventaService.guardar(venta));
                return new ResponseEntity<>(new ApiResponse<>("Venta creada ", dto, true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>("Venta no encontrada ", null, false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<DetalleVenta> mapearDetalles(List<DetalleVentaPostDTO> detalleDTOs, Venta venta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        for (DetalleVentaPostDTO dto : detalleDTOs) {
            Vehiculo vehiculo = vehiculoService.obtener(dto.getVehiculoId()).orElse(null);
            if (vehiculo == null) {
                throw new RuntimeException("Vehículo no encontrado con ID: " + dto.getVehiculoId());
            }
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVehiculo(vehiculo);
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            detalle.setVenta(venta);
            detalles.add(detalle);
        }
        return detalles;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Integer id) {
        try {
            Venta venta = ventaService.obtener(id).orElse(null);
            if (venta != null) {
                ventaService.eliminar(venta.getId());
                return new ResponseEntity<>(new ApiResponse<>("Venta dada de baja ", null, true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse<>("Venta no encontrada ", null, false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

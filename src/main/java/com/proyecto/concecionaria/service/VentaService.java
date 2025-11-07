package com.proyecto.concecionaria.service;

import com.proyecto.concecionaria.DTOs.DetallesVenta.DetalleVentaPostDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaGetDTO;
import com.proyecto.concecionaria.DTOs.Venta.VentaMapper;
import com.proyecto.concecionaria.DTOs.Venta.VentaPostDTO;
import com.proyecto.concecionaria.entity.*;
import com.proyecto.concecionaria.interfaz.VentaInterfaz;
import com.proyecto.concecionaria.repository.ClienteRepository;
import com.proyecto.concecionaria.repository.UsuarioRepository;
import com.proyecto.concecionaria.repository.VehiculoRepository;
import com.proyecto.concecionaria.repository.VentaRepository;

import java.util.List;
import java.util.Optional;

import com.proyecto.concecionaria.util.EstadoPagos;
import com.proyecto.concecionaria.util.EstadoVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService implements VentaInterfaz {

    @Autowired
    private VentaRepository repo;
    @Autowired
    private VentaMapper mapper;
    @Autowired
    private ClienteRepository clienterepo;
    @Autowired
    private UsuarioRepository userepo;
    @Autowired
    private VehiculoRepository vehiculoRepo;


    @Override
    public VentaGetDTO crear(VentaPostDTO post) {
        Cliente cliente = clienterepo.findById(post.getClienteId()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Usuario usuario = userepo.findById(post.getEmpleadoId()).orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        validarStockVehiculos(post.getDetalleVentas());

        Venta venta = mapper.toEntity(post, cliente, usuario);
        repo.save(venta);

        actualizarStockVehiculos(post.getDetalleVentas());

        return mapper.toDTO(venta);
    }


    private void validarStockVehiculos(List<DetalleVentaPostDTO> detalles) {
        for (DetalleVentaPostDTO detalle : detalles) {
            Vehiculo vehiculo = vehiculoRepo.findById(detalle.getVehiculoId()).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + detalle.getVehiculoId()));

            if (vehiculo.getStock() < detalle.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el vehículo: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + ". Stock disponible: " + vehiculo.getStock() + ", solicitado: " + detalle.getCantidad());
            }
        }
    }

    private void actualizarStockVehiculos(List<DetalleVentaPostDTO> detalles) {
        for (DetalleVentaPostDTO detalle : detalles) {
            Vehiculo vehiculo = vehiculoRepo.findById(detalle.getVehiculoId()).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

            int nuevoStock = vehiculo.getStock() - detalle.getCantidad();
            vehiculo.setStock(nuevoStock);
            vehiculoRepo.save(vehiculo);
        }
    }

    @Override
    public Optional<VentaGetDTO> obtener(Integer id) {
        return repo.findById(id).filter(Venta::isActivo).map(mapper::toDTO);
    }

    @Override
    public List<VentaGetDTO> listar() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    @Transactional
    public VentaGetDTO cancelar(Integer id) {
        Venta venta = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));

        venta.getDetalleVentas().size();
        venta.getPagos().size();
        venta.setActivo(false);
        venta.setEstado(EstadoVenta.CANCELADO);

        revertirStockVehiculos(venta.getDetalleVentas());
        venta.limpiarPagos();

        // 1. Limpiar relaciones y GUARDAR los cambios
        Cliente cliente = venta.getCliente();
        Usuario empleado = venta.getEmpleado();

        limpiarRelacionesBidireccionales(venta);

        // 2. Guardar cliente y usuario para persistir los cambios
        if (cliente != null) {
            clienterepo.save(cliente); // ✅ GUARDAR los cambios en cliente
        }
        if (empleado != null) {
            userepo.save(empleado); // ✅ GUARDAR los cambios en usuario
        }

        repo.save(venta);
        return mapper.toDTO(venta);
    }

    private void revertirStockVehiculos(List<DetalleVenta> detalles) {
        for (DetalleVenta detalle : detalles) {
            Vehiculo vehiculo = detalle.getVehiculo();
            vehiculo.setStock(vehiculo.getStock() + detalle.getCantidad());
            vehiculoRepo.save(vehiculo);
        }
    }

    private void limpiarRelacionesBidireccionales(Venta venta) {
        // Limpiar relación con cliente
        if (venta.getCliente() != null) {
            boolean removed = venta.getCliente().getVentas().removeIf(v -> v.getId().equals(venta.getId()));
            System.out.println("Cliente ventas removidas: " + removed);
        }

        // Limpiar relación con empleado
        if (venta.getEmpleado() != null) {
            boolean removed = venta.getEmpleado().getVentas().removeIf(v -> v.getId().equals(venta.getId()));
            System.out.println("Empleado ventas removidas: " + removed);
        }

        // Limpiar relación con vehículos en los detalles
        venta.getDetalleVentas().forEach(detalle -> {
            if (detalle.getVehiculo() != null) {
                boolean removed = detalle.getVehiculo().getDetalleVentas().removeIf(d -> d.getId().equals(detalle.getId()));
                System.out.println("Vehículo detalles removidos: " + removed);
                vehiculoRepo.save(detalle.getVehiculo()); // ✅ Guardar vehículo también
            }
        });
    }
}

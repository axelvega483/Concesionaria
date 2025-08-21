package com.proyecto.concecionaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.proyecto.concecionaria.util.EstadoPagos;
import com.proyecto.concecionaria.util.EstadoVenta;
import com.proyecto.concecionaria.util.FrecuenciaPago;
import com.proyecto.concecionaria.util.MetodoPago;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venta")
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La fecha de la venta no puede estar vacía")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull(message = "El total no puede estar vacío")
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    @Column(nullable = false)
    private BigDecimal total;

    @NotNull(message = "La frecuencia de pago no puede estar vacía")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FrecuenciaPago frecuenciaPago;

    @NotNull(message = "Debe incluir al menos un detalle de venta")
    @Size(min = 1, message = "Debe haber al menos un producto en la venta")
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("venta")
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    @NotNull(message = "La venta debe estar asociada a un cliente")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("ventas")
    private Cliente cliente;

    @NotNull(message = "La venta debe ser registrada por un empleado")
    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    @JsonIgnoreProperties("ventas")
    private Usuario empleado;

    @NotNull(message = "Debe incluir al menos un pago de venta")
    @Size(min = 1, message = "Debe haber al menos un pago en la venta")
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("venta")
    private List<Pagos> pagos = new ArrayList<>();

    @Column
    private Double entrega; // porcentaje

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    @Column
    private Integer cuotas;

    @Column(nullable = false)
    private Boolean activo = true;

    public void agregarPago(Pagos pago) {
        pagos.add(pago);
        pago.setVenta(this);
    }
    public void limpiarPagos() {
        pagos.forEach(p -> p.setVenta(null)); // Romper vínculo
        pagos.clear(); // Limpiar lista
    }

    public void generarPagos() {
        limpiarPagos();
        if (frecuenciaPago == FrecuenciaPago.UNICO) {
            Pagos pagoUnico = new Pagos();
            pagoUnico.setFechaPago(fecha);
            pagoUnico.setMonto(total);
            pagoUnico.setEstado(EstadoPagos.PENDIENTE);
            pagoUnico.setMetodoPago(MetodoPago.PENDIENTE);
            pagoUnico.setActivo(true);
            agregarPago(pagoUnico);
        } else {
            generarPagosConCuotas();
        }
    }

    public void generarPagosConCuotas() {
        LocalDate fechaPago = this.fecha;
        BigDecimal montoEntrega = BigDecimal.valueOf(entrega != null ? entrega : 0.0);
        BigDecimal montoRestante = total.subtract(montoEntrega);

        int cantidadCuotas = (cuotas != null && cuotas > 0) ? cuotas : 1;
        BigDecimal montoPorCuota = montoRestante.divide(BigDecimal.valueOf(cantidadCuotas), 2, RoundingMode.HALF_UP);

        if (montoEntrega.compareTo(BigDecimal.ZERO) > 0) {
            Pagos pagoInicial = new Pagos();
            pagoInicial.setFechaPago(fechaPago);
            pagoInicial.setMonto(montoEntrega);
            pagoInicial.setEstado(EstadoPagos.PENDIENTE);
            pagoInicial.setMetodoPago(MetodoPago.PENDIENTE);
            pagoInicial.setActivo(true);
            agregarPago(pagoInicial);
        }

        BigDecimal sumaCuotas = BigDecimal.ZERO;

        for (int i = 0; i < cantidadCuotas; i++) {
            Pagos cuota = new Pagos();
            cuota.setFechaPago(fechaPago.plusMonths(i + 1));

            // Ajustar la última cuota si hay diferencia por redondeo
            if (i == cantidadCuotas - 1) {
                BigDecimal montoUltimaCuota = montoRestante.subtract(sumaCuotas);
                cuota.setMonto(montoUltimaCuota);
            } else {
                cuota.setMonto(montoPorCuota);
                sumaCuotas = sumaCuotas.add(montoPorCuota);
            }

            cuota.setEstado(EstadoPagos.PENDIENTE);
            cuota.setMetodoPago(MetodoPago.PENDIENTE);
            cuota.setActivo(true);
            agregarPago(cuota);
        }
    }


    public void actualizarSaldo() {
        if (getSaldoRestante().compareTo(BigDecimal.ZERO) <= 0) {
            this.estado = EstadoVenta.FINALIZADO;
        } else {
            this.estado = EstadoVenta.ACTIVO;
        }

    }

    public BigDecimal getSaldoRestante() {
        return total.subtract(
                pagos.stream()
                        .filter(p -> p.getEstado() == EstadoPagos.PAGADO)
                        .map(Pagos::getMonto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}

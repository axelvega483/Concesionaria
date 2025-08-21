package com.proyecto.concecionaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.proyecto.concecionaria.util.EstadoPagos;
import com.proyecto.concecionaria.util.MetodoPago;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "pagos")
public class Pagos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private LocalDate fechaPago;

    @NotNull(message = "El metodo de pago no puede estar vacía")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @NotNull(message = "El monto no puede estar vacía")
    @Column(nullable = false)
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnoreProperties("pagos")
    @NotNull(message = "Los pagos debe estar registrada en una venta")
    private Venta venta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPagos estado;

    @Column(nullable = false)
    private Boolean activo = true;

    public void confirmarPago() {
        this.estado = EstadoPagos.PAGADO;
        this.venta.actualizarSaldo();
    }
}

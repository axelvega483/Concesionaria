package com.proyecto.concecionaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "detalleventa")
public class DetalleVenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    @JsonIgnoreProperties("detalleVentas")
    @NotNull(message = "El vehiculo no puede ser nulo")
    private Vehiculo vehiculo;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;
    
    @NotNull(message = "El precio unitario no puede ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "El precio unitario debe ser mayor a 0")
    @Column(nullable = false)
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnoreProperties("detalleVentas")
    @NotNull(message = "La venta no puede ser nula")
    private Venta venta;
}

package com.proyecto.concecionaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import java.time.LocalDateTime;
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
@Table(name = "compra")
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "La fecha de la venta no puede estar vacía")
    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotNull(message = "El total no puede estar vacío")
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    @Column(nullable = false)
    private Double total;

    @NotNull(message = "Debe incluir al menos un detalle de compra")
    @Size(min = 1, message = "Debe haber al menos un producto en la compra")
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("compra")
    private List<DetalleCompra> detalleCompras = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("compras")
    @NotNull(message = "La compra debe estar asociada a un cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    @JsonIgnoreProperties("compras")
    @NotNull(message = "La compra debe estar registrada por un empleado")
    private Usuario empleado;

    @Column(nullable = false)
    private Boolean activo = true;
}

package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(
    properties =
      "idFactura, " +
      "asignacion.usuario.nombre, " +
      "asignacion.ticket.asunto, " +
      "subtotal, iva, impuesto"
)
@View(
    members =
    "Cabecera [ idFactura; asignacion.usuario.nombre, asignacion.ticket.asunto ] " +
    "Valores  [ subtotal; iva; impuesto ] " +
    "Detalles [ detalles ]"
)
@Getter @Setter
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFactura")
    private Long idFactura;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idAsignacion")           
    @ReferenceView("Simple")
    private Asignacion asignacion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal iva;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal impuesto;

    @Column(length = 500)
    @Stereotype("TEXTAREA")
    private String detalles;
}

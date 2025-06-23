package com.tuempresa.ticket_proyect.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties = "asunto, estado, prioridad, fechaCreacion, fechaCierre")
@View(members =
    "asunto, estado, prioridad;" +
    "fechaCreacion, fechaCierre;" +
    "cliente;" +
    "descripcion;" +
    "Asignaciones { asignaciones }"
)
@Getter @Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "idTicket")
    private Long idTicket;

    @Column(length = 50, nullable = false)
    private String asunto;

    @Column(length = 5000)
    @Stereotype("TEXTAREA")
    private String descripcion;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.FechaActualCalculator.class)
    private Date fechaCreacion;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @DefaultValueCalculator(EstadoTicketDefault.class)
    private EstadoTicket estado;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoUrgencia prioridad;



    @ManyToOne(optional = false)
    @ReferenceView("Simple")
    private Cliente cliente;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ListProperties("usuario.nombre, fechaAsignacion, horaAsignacion")
    private Collection<Asignacion> asignaciones;
}

package com.tuempresa.ticket_proyect.modelo;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@View(members = 
    "Datos de Asignación { usuario, ticket };" +
    "Tiempos            { fechaAsignacion, horaAsignacion }"
)
@Getter @Setter
public class Asignacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario")
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.AsignadorProporcionalCalculator.class)
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTicket")
    private Ticket ticket;

    /** Fecha (solo día), por defecto hoy */
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.FechaActualCalculator.class)
    private Date fechaAsignacion;

    /** Hora (solo hora), por defecto hora actual */
    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.HoraActualCalculator.class)
    private Date horaAsignacion;

}

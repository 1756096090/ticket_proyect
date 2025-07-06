// Asignacion.java
package com.tuempresa.ticket_proyect.modelo;

import java.io.*;
import java.math.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Table(name = "Asignacion")
@Views({
    @View(name = "Simple", members = "usuario, ticket"),
    @View(members =
        "Datos de Asignación { usuario, ticket };" +
        "Tiempos            { fechaAsignacion, horaAsignacion };" +
        "Inicio y Fin       { horaInicioTicket; horaFinTicket }"
    )
})
@Getter @Setter
public class Asignacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.AsignadorProporcionalCalculator.class)
    private Usuario usuario;

    // Y contra la columna idTicket
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTicket", nullable = false)
    private Ticket ticket;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.FechaActualCalculator.class)
    private Date fechaAsignacion;

    @Column(length = 8, nullable = false)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.HoraActualCalculator.class)
    @Stereotype("TIME")
    private String horaAsignacion;

    @Column(length = 5) @Stereotype("TIME")
    private String horaInicioTicket;

    @Column(length = 5) @Stereotype("TIME")
    private String horaFinTicket;

    @Transient
    public BigDecimal getHorasTrabajadas() {
        if (horaInicioTicket == null || horaFinTicket == null) {
            return BigDecimal.ZERO;
        }
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("H:mm");
            LocalTime inicio = LocalTime.parse(horaInicioTicket, fmt);
            LocalTime fin    = LocalTime.parse(horaFinTicket,    fmt);
            Duration dur     = Duration.between(inicio, fin);
            return BigDecimal.valueOf(dur.toMinutes() / 60.0);
        } catch (DateTimeParseException e) {
            return BigDecimal.ZERO;
        }
    }
}

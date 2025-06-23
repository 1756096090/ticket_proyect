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
@Views({
    @View(name="Simple", members="usuario, ticket"),
    @View(members=
        "Datos de Asignación { usuario, ticket };" +
        "Tiempos            { fechaAsignacion, horaAsignacion };" +
        "Inicio y Fin       { horaInicioTicket; horaFinTicket }"
    )
})
@Getter @Setter
public class Asignacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="idUsuario")
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.AsignadorProporcionalCalculator.class)
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="idTicket")
    private Ticket ticket;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.FechaActualCalculator.class)
    private Date fechaAsignacion;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    @DefaultValueCalculator(com.tuempresa.ticket_proyect.calculadores.HoraActualCalculator.class)
    @Stereotype("TIME")
    private Date horaAsignacion;

    // Ahora son Strings, para evitar mismatch
    @Column(length=5)
    @Stereotype("TIME")
    private String horaInicioTicket;

    @Column(length=5)
    @Stereotype("TIME")
    private String horaFinTicket;

    /**
     * Parsea las cadenas HH:mm y calcula la diferencia en horas
     */
    @Transient
    public BigDecimal getHorasTrabajadas() {
        if (horaInicioTicket == null || horaFinTicket == null) return BigDecimal.ZERO;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("H:mm");
            LocalTime inicio = LocalTime.parse(horaInicioTicket, fmt);
            LocalTime fin    = LocalTime.parse(horaFinTicket,   fmt);
            Duration d       = Duration.between(inicio, fin);
            double horas     = d.toMinutes() / 60.0;
            return BigDecimal.valueOf(horas);
        }
        catch(Exception e) {
            return BigDecimal.ZERO; // en caso de parseo fallido
        }
    }
}

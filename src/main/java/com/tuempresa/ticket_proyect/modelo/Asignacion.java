package com.tuempresa.ticket_proyect.modelo;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@IdClass(AsignacionId.class)
@View(members=
    "usuario;" +
    "ticket;" + 
    "fechaSolicitud, horaSolicitud"
)
public class Asignacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @Id
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="idTicket")
    private Ticket ticket;
    
    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaSolicitud;

    // Getters y Setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getHoraSolicitud() {
        return horaSolicitud;
    }

    public void setHoraSolicitud(Date horaSolicitud) {
        this.horaSolicitud = horaSolicitud;
    }
}

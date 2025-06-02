package com.tuempresa.ticket_proyect.modelo;

import java.io.*;
import java.util.*;

/**
 * Clase auxiliar para representar la clave compuesta de Asignacion.
 */
public class AsignacionId implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long usuario; // coincide con Usuario.idUsuario
    private Long ticket;  // coincide con Ticket.idTicket

    public AsignacionId() {        
    }

    public AsignacionId(Long usuario, Long ticket) {
        this.usuario = usuario;
        this.ticket = ticket;
    }


    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        AsignacionId that = (AsignacionId) o;
        return Objects.equals(usuario, that.usuario) &&
               Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, ticket);
    }
}

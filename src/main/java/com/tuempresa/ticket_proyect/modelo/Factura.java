package com.tuempresa.ticket_proyect.modelo;
import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Tab(properties="idFactura, usuario.nombre, ticket.asunto, subtotal, iva, impuesto")
@View(members=
    "idFactura;" +
    "usuario;" +
    "ticket;" +
    "subtotal, iva, impuesto;" +
    "detalles"
)
public class Factura {

    @Id
    @Column(name="idFactura")
    private Long idFactura;
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")
    private Usuario usuario;
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="idTicket")
    private Ticket ticket;
    
    @Column(nullable=false, precision=12, scale=2)
    private BigDecimal subtotal;
    
    @Column(nullable=false, precision=12, scale=2)
    private BigDecimal iva;
    
    @Column(nullable=false, precision=12, scale=2)
    private BigDecimal impuesto;
    
    @Column(length=500)
    @Stereotype("TEXTAREA")
    private String detalles;

    // Getters y Setters

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}

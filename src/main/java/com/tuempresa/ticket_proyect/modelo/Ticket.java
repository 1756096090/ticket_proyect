package com.tuempresa.ticket_proyect.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Tab(properties="asunto, estado, prioridad, fechaCreacion, fechaCierre")
@View(members=
    "asunto, estado, prioridad;" +
    "fechaCreacion, fechaCierre;" +
    "descripcion;" +
    "Asignaciones { asignaciones } " +
    "Facturas     { facturas }"
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name="idTicket")
    private Long idTicket;
    
    @Column(length=200, nullable=false)
    private String asunto;
    
    @Column(length=5000)
    @Stereotype("TEXTAREA")
    private String descripcion;
    
    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    
    @Column(length=50)
    private String estado;
    
    @Column(length=50)
    private String prioridad;
    
    // Si quisieras en el futuro asociar una tarifa específica al ticket:
    // @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="idTarifa")
    // private Tarifa tarifa;

    @OneToMany(mappedBy="ticket", fetch=FetchType.LAZY)
    @ListProperties("usuario.nombre, fechaSolicitud, horaSolicitud")
    private Collection<Asignacion> asignaciones;
    
    @OneToMany(mappedBy="ticket", fetch=FetchType.LAZY)
    @ListProperties("usuario.nombre, subtotal, iva, impuesto")
    private Collection<Factura> facturas;

    // ——————————————
    // Getters y Setters
    // ——————————————

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Collection<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Collection<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public Collection<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Collection<Factura> facturas) {
        this.facturas = facturas;
    }

    // Si en el futuro decides relacionar directamente una tarifa:
    // public Tarifa getTarifa() {
    //     return tarifa;
    // }
    // public void setTarifa(Tarifa tarifa) {
    //     this.tarifa = tarifa;
    // }
}

package com.tuempresa.ticket_proyect.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Tab(properties="nombre, mail, sede, activo, rol")
@View(members=
    "nombre, mail, sede, activo;" +
    "rol;" +
    "Asignaciones { asignaciones } " + 
    "Facturas { facturas }"
)
public class Usuario {

    @Id
    @Column(name="idUsuario")
    private Long idUsuario;
    
    @Column(length=100, nullable=false)
    private String nombre;
    
    @Column(length=100, nullable=false)
    private String mail;
    
    @Column(length=100)
    private String sede;
    
    @Column
    private boolean activo;
    
    @Column(length=50)
    private String rol;
    
    // Relación con Asignación (un usuario puede tener varias asignaciones)
    @OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)
    @ListProperties("ticket.asunto, fechaSolicitud, horaSolicitud")
    private Collection<Asignacion> asignaciones;
    
    // Relación con Factura (un usuario puede generar varias facturas)
    @OneToMany(mappedBy="usuario", fetch=FetchType.LAZY)
    @ListProperties("ticket.asunto, subtotal, iva, impuesto")
    private Collection<Factura> facturas;

    // Getters y Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
}

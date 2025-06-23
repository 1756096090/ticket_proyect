package com.tuempresa.ticket_proyect.modelo;

import java.io.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Views({
    @View(name = "Simple", members = "nombre"),
    @View(name = "Default",
        members =
            "nombre;" +
            "empresa;" +
            "Datos Encargado { nombreEncargado; mailEncargado; telefonoEncargado };" +
            "Contacto { mail; numeroTelefono }"
    )
})
@Getter @Setter
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "idCliente", nullable = false, updatable = false)
    private Long idCliente;

    @Column(length = 50, nullable = false)
    private String nombre; // Persona con el problema

    @Column(length = 50, nullable = false)
    private String mail;

    @Column(length = 20)
    @Stereotype("TELEPHONE")
    private String numeroTelefono;

    @ManyToOne(optional = false)
    @ReferenceView("Simple")
    private Empresa empresa;

    @Column(length = 50)
    private String nombreEncargado;

    @Column(length = 50)
    private String mailEncargado;

    @Column(length = 20)
    @Stereotype("TELEPHONE")
    private String telefonoEncargado;
}

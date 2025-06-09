package com.tuempresa.ticket_proyect.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties = "nombre, mail, sede, activo, rol")
@View(members =
    "Datos Personales { nombre, mail; sede, rol; activo };" +
    "Asignaciones         { asignaciones }"
)
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    @Required
    private Sede sede;

    @Column(nullable = false)
    private boolean activo;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ListProperties("ticket.asunto, fechaAsignacion, horaAsignacion")
    private Collection<Asignacion> asignaciones;
}

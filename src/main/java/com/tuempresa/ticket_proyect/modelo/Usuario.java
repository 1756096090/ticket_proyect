package com.tuempresa.ticket_proyect.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.tuempresa.ticket_proyect.calculadores.*;

import lombok.*;

@Entity
@Tab(properties = "nombre, cedula, mail, sede, activo")
@View(members =
    "Datos Personales { nombre, cedula; mail; activo; especialidad };" +
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

    @Column(length = 10, nullable = false, unique = true)
    private String cedula;

    @Column(length = 50, nullable = false)
    private String mail;

    @Column(nullable = false)
    private boolean activo;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Especialidad especialidad; // ← campo agregado

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ListProperties("ticket.asunto, fechaAsignacion, horaAsignacion")
    private Collection<Asignacion> asignaciones;

    // Validación automática al guardar o actualizar
    @PrePersist @PreUpdate
    private void validarCedula() {
        if (!ValidadorCedulaEcuador.validar(cedula)) {
            throw new IllegalArgumentException("La cédula ingresada no es válida según las reglas ecuatorianas.");
        }
    }
}

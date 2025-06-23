package com.tuempresa.ticket_proyect.modelo;

import java.io.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.tuempresa.ticket_proyect.calculadores.*;

import lombok.*;

@Entity
@View(name = "Simple", members = "nombreEmpresa, ruc, direccion")
@Getter @Setter
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "idEmpresa")
    private Long idEmpresa;

    @Column(length = 100, nullable = false)
    private String nombreEmpresa;

    @Column(length = 13, nullable = false)
    private String ruc; // Como String para preservar ceros iniciales

    @Column(length = 100)
    private String direccion;


    @PrePersist @PreUpdate
    private void validarRucEcuador() {
        if (!ValidadorRucEcuador.validar(this.ruc)) {
            throw new IllegalArgumentException("El RUC ingresado no es válido según las reglas ecuatorianas.");
        }
    }
}

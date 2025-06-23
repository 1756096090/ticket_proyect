package com.tuempresa.ticket_proyect.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties = "especialidad, precio")
@View(members =
    "especialidad; precio"
)
@Getter @Setter
public class TarifaEspecialidad extends Tarifa {

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Especialidad especialidad;
}

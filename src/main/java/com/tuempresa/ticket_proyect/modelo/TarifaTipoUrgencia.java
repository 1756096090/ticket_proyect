package com.tuempresa.ticket_proyect.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties = "tipoUrgencia, precio")
@View(members =
    "tipoUrgencia; precio"
)
@Getter @Setter
public class TarifaTipoUrgencia extends Tarifa {

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private TipoUrgencia tipoUrgencia;
}

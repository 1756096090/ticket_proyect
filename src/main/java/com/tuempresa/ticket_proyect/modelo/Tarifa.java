package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

/**
 * Entidad que almacena las tarifas generales (por urgencia, especialista, etc.).
 */
@Entity
@Tab(properties="descripcion, tipoUrgencia, especialista, precio")
@View(members=
    "descripcion;" +
    "tipoUrgencia, especialista;" +
    "precio"
)

@Getter
@Setter

public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name="idTarifa")
    private Long idTarifa;

    /**
     * Una descripción libre para identificar la tarifa,
     * por ejemplo: "Urgencia Alta", "Soporte Nocturno", etc.
     */
    @Column(length=100, nullable=false)
    private String descripcion;

    /**
     * Nombre del especialista o grupo que aplica esta tarifa.
     * Ejemplo: "Soporte HW", "Soporte SW", etc.
     * Puede dejarse en NULL si no aplica.
     */
    @Column(length=100)
    private String especialista;

    /**
     * El tipo de urgencia que define esta tarifa.
     * Usamos un enum para normalizar valores: BAJA, MEDIA, ALTA, INMEDIATA.
     */
    @Column(length=50, nullable=false)
    @Enumerated(EnumType.STRING)
    private TipoUrgencia tipoUrgencia;

    /**
     * Precio asociado a esta tarifa (p.ej. 50.00, 100.00, etc.).
     */
    @Column(nullable=false, precision=12, scale=2)
    private BigDecimal precio;

    
}

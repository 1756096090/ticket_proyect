package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties = 
    "idFactura, asignacion.usuario.nombre, asignacion.ticket.asunto, " +
    "horasTrabajadas, subtotal, iva, impuesto")
@View(members =
    "Cabecera [ asignacion ];" +
    "Horas    [ horasTrabajadas ];" +
    "Valores  [ subtotal; iva; impuesto ];" +
    "Detalles [ detalles ]"
)
@Getter @Setter
public class Factura {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long idFactura;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    private Asignacion asignacion;

    @Column(length = 500)
    @Stereotype("TEXTAREA")
    private String detalles;

    /** Horas trabajadas, calculadas dinámicamente */
    @ReadOnly
    @Depends("asignacion.horaInicioTicket, asignacion.horaFinTicket")
    public BigDecimal getHorasTrabajadas() {
        if (asignacion == null) return BigDecimal.ZERO;
        BigDecimal h = asignacion.getHorasTrabajadas();
        return h != null ? h : BigDecimal.ZERO;
    }

    /** Subtotal = (tarifa especialidad + tarifa urgencia) * horas */
    @ReadOnly
    @Depends("asignacion, horasTrabajadas")
    public BigDecimal getSubtotal() {
        if (asignacion == null) return BigDecimal.ZERO;
        BigDecimal tarifa = BigDecimal.ZERO;

        if (asignacion.getUsuario() != null) {
            Especialidad esp = asignacion.getUsuario().getEspecialidad();
            if (esp != null) {
                TarifaEspecialidad te = TarifaService.buscarPorEspecialidad(esp);
                if (te != null) tarifa = tarifa.add(te.getPrecio());
            }
        }
        if (asignacion.getTicket() != null) {
            TipoUrgencia urg = asignacion.getTicket().getPrioridad();
            if (urg != null) {
                TarifaTipoUrgencia tu = TarifaService.buscarPorUrgencia(urg);
                if (tu != null) tarifa = tarifa.add(tu.getPrecio());
            }
        }
        return tarifa.multiply(getHorasTrabajadas());
    }

    /** 12% de IVA sobre subtotal */
    @ReadOnly
    @Depends("subtotal")
    public BigDecimal getIva() {
        return getSubtotal().multiply(new BigDecimal("0.12"));
    }

    /** Subtotal + IVA */
    @ReadOnly
    @Depends("subtotal, iva")
    public BigDecimal getImpuesto() {
        return getSubtotal().add(getIva());
    }

}

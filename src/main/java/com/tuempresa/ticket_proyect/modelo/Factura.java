package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties =
    "idFactura, asignacion.usuario.nombre, asignacion.ticket.asunto, horasTrabajadas, subtotal, iva, total"
)
@View(
    name="Default",
    members=
      "Actions { " +
        "<action action=\"Factura.llenarFactura\"/> " +
      "};" +
      "Asignación { asignacion; detalles }; " +
      "Cálculos { horasTrabajadas; subtotal; iva; total }"
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

    @Stereotype("TEXTAREA")
    private String detalles;

    @Column(nullable = false)
    private BigDecimal horasTrabajadas;

    @Depends("asignacion, horasTrabajadas")
    public BigDecimal getSubtotal() {
        if (asignacion == null) return BigDecimal.ZERO;
        BigDecimal tarifa = BigDecimal.ZERO;
        if (asignacion.getUsuario() != null) {
            var esp = asignacion.getUsuario().getEspecialidad();
            var te  = TarifaService.buscarPorEspecialidad(esp);
            if (te != null) tarifa = tarifa.add(te.getPrecio());
        }
        if (asignacion.getTicket() != null) {
            var urg = asignacion.getTicket().getPrioridad();
            var tu  = TarifaService.buscarPorUrgencia(urg);
            if (tu != null) tarifa = tarifa.add(tu.getPrecio());
        }
        return tarifa.multiply(horasTrabajadas != null ? horasTrabajadas : BigDecimal.ZERO);
    }

    @Depends("subtotal")
    public BigDecimal getIva() {
        return getSubtotal().multiply(new BigDecimal("0.12"));
    }

    /** Total (subtotal + IVA) */
    
    @Depends("subtotal, iva")
    @Column(name = "total") 
    public BigDecimal getTotal() {
        return getSubtotal().add(getIva());
    }
}

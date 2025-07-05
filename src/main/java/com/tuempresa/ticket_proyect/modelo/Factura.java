package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties =
    "idFactura, nombreUsuarioAsignado, asuntoTicketAsignado, " +
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

    @Stereotype("TEXTAREA")
    private String detalles;

    // Evita errores al mostrar asignacion.usuario.nombre en @Tab
    @Depends("asignacion.usuario.nombre")
    public String getNombreUsuarioAsignado() {
        return (asignacion != null && asignacion.getUsuario() != null)
            ? asignacion.getUsuario().getNombre()
            : "(sin usuario)";
    }

    // Evita errores al mostrar asignacion.ticket.asunto en @Tab
    @Depends("asignacion.ticket.asunto")
    public String getAsuntoTicketAsignado() {
        return (asignacion != null && asignacion.getTicket() != null)
            ? asignacion.getTicket().getAsunto()
            : "(sin ticket)";
    }

    /** Horas trabajadas, obtenidas de la Asignación */
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
            var esp = asignacion.getUsuario().getEspecialidad();
            if (esp != null) {
                var te = TarifaService.buscarPorEspecialidad(esp);
                if (te != null) tarifa = tarifa.add(te.getPrecio());
            }
        }
        if (asignacion.getTicket() != null) {
            var urg = asignacion.getTicket().getPrioridad();
            if (urg != null) {
                var tu = TarifaService.buscarPorUrgencia(urg);
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

    // Setter personalizado para registrar en consola
    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
        if (asignacion == null) {
            System.out.println("Factura: NO se recibió ninguna Asignación.");
        } else if (asignacion.getHoraInicioTicket() == null || asignacion.getHoraFinTicket() == null) {
            System.out.println("Factura: faltan datos de horaInicio o horaFin en la Asignación.");
        } else {
            BigDecimal horas = asignacion.getHorasTrabajadas();
            System.out.println("Factura: horas trabajadas calculadas = " + horas);
        }
    }
}

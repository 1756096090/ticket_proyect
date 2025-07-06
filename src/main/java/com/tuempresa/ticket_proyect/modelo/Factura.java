package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

@Entity
@Tab(properties =
    "idFactura, " +
    "horasTrabajadas, subtotal, iva, impuesto")
@View(
	    name = "Default",
	    members =
	        "Operations { " +
	          "<action action=\"Factura.llenarFactura\"/>" +
	        " };" +
	        "Cabecera [ asignacion ];" +
	        "Horas    [ horasTrabajadas ];" +
	        "Valores  [ subtotal; iva; impuesto ];" 
	)
@Getter @Setter
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long idFactura;
    
    @Column(nullable = false)
    private BigDecimal horasTrabajadas;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    private Asignacion asignacion;

    @Stereotype("TEXTAREA")
    private String detalles;

    @Depends("asignacion.usuario.nombre")
    public String getNombreUsuarioAsignado() {
        return (asignacion != null && asignacion.getUsuario() != null)
            ? asignacion.getUsuario().getNombre()
            : "(sin usuario)";
    }

    @Depends("asignacion.ticket.asunto")
    public String getAsuntoTicketAsignado() {
        return (asignacion != null && asignacion.getTicket() != null)
            ? asignacion.getTicket().getAsunto()
            : "(sin ticket)";
    }

    @ReadOnly
    @Depends("asignacion.horaInicioTicket, asignacion.horaFinTicket")
    public BigDecimal getHorasTrabajadas() {
        if (asignacion == null) return BigDecimal.ZERO;
        BigDecimal h = asignacion.getHorasTrabajadas();
        return h != null ? h : BigDecimal.ZERO;
    }

    public void setHorasTrabajadas(BigDecimal horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }
    
   
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

    @ReadOnly
    @Depends("subtotal")
    public BigDecimal getIva() {
        return getSubtotal().multiply(new BigDecimal("0.12"));
    }

    @ReadOnly
    @Depends("subtotal, iva")
    public BigDecimal getImpuesto() {
        return getSubtotal().add(getIva());
    }

    // Deja este setter sólo para asignar; la lógica de cálculo la dispara tu acción
    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }
}

package com.tuempresa.ticket_proyect.modelo;

import java.math.*;

import org.openxava.actions.*;

public class LlenarFacturaAction extends ViewBaseAction {
    @Override
    public void execute() throws Exception {
        Factura factura = (Factura) getView().getEntity();
        if (factura.getAsignacion() == null) {
            addError("Debe seleccionar primero una asignación");
            return;
        }
        // 1) Calculamos horas trabajadas
        BigDecimal horas = factura.getAsignacion().getHorasTrabajadas();
        BigDecimal valorHoras = horas != null ? horas : BigDecimal.ZERO;
        factura.setHorasTrabajadas(valorHoras);

        // 2) Obtenemos los valores dependientes
        BigDecimal subtotal = factura.getSubtotal();
        BigDecimal iva      = factura.getIva();
        BigDecimal total    = factura.getTotal();

        // 3) Si ya está persistida, permitimos que el servidor recalcule todo
        if (factura.getIdFactura() != null) {
            getView().recalculateProperties();
        }
        // 4) Si es nueva, notificamos manualmente cada valor al cliente
        else {
            getView().setValueNotifying("horasTrabajadas", valorHoras);
            getView().setValueNotifying("subtotal",          subtotal);
            getView().setValueNotifying("iva",               iva);
            getView().setValueNotifying("total",             total);
        }

        addMessage("Datos de factura llenados correctamente");
    }
}

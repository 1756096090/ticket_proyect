package com.tuempresa.ticket_proyect.modelo;

import org.openxava.calculators.*;

public class EstadoTicketDefault implements ICalculator {

    @Override
    public Object calculate() throws Exception {
        return EstadoTicket.NUEVO;
    }
}

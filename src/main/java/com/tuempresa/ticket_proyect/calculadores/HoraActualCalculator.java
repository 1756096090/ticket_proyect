package com.tuempresa.ticket_proyect.calculadores;

import java.sql.*;

import org.openxava.calculators.*;

public class HoraActualCalculator implements ICalculator {
    @Override
    public Object calculate() {
        return new Time(System.currentTimeMillis());
    }
}

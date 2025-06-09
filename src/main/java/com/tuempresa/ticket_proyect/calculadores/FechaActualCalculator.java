package com.tuempresa.ticket_proyect.calculadores;

import java.util.*;

import org.openxava.calculators.*;

public class FechaActualCalculator implements ICalculator {
    
    @Override
    public Object calculate() throws Exception {
        return new Date(); 
    }
}

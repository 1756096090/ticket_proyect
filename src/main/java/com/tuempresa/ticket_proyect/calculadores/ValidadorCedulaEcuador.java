package com.tuempresa.ticket_proyect.calculadores;

public class ValidadorCedulaEcuador {

    public static boolean validar(String cedula) {
        if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d+")) {
            return false;
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito > 5) {
            return false;
        }

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }
            suma += digito;
        }

        int verificadorCalculado = 10 - (suma % 10);
        if (verificadorCalculado == 10) verificadorCalculado = 0;

        int verificadorReal = Character.getNumericValue(cedula.charAt(9));
        return verificadorCalculado == verificadorReal;
    }
}

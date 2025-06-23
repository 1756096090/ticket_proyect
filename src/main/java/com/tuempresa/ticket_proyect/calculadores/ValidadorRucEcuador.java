package com.tuempresa.ticket_proyect.calculadores;

public class ValidadorRucEcuador {

    public static boolean validar(String ruc) {
        if (ruc == null || ruc.length() != 13 || !ruc.matches("\\d+")) return false;

        int provincia = Integer.parseInt(ruc.substring(0, 2));
        int tercerDigito = Character.getNumericValue(ruc.charAt(2));

        if (provincia < 1 || provincia > 24) return false;

        if (tercerDigito >= 0 && tercerDigito <= 5) {
            return validarCedula(ruc.substring(0, 10)) && ruc.endsWith("001");
        } else if (tercerDigito == 6) {
            return validarEntidadPublica(ruc.substring(0, 9)) && ruc.endsWith("001");
        } else if (tercerDigito == 9) {
            return validarSociedadPrivada(ruc.substring(0, 10)) && ruc.endsWith("001");
        }

        return false;
    }

    private static boolean validarCedula(String cedula) {
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }
            suma += digito;
        }
        int verificador = 10 - (suma % 10);
        if (verificador == 10) verificador = 0;
        return verificador == Character.getNumericValue(cedula.charAt(9));
    }

    private static boolean validarEntidadPublica(String ruc9) {
        int[] coef = {3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;
        for (int i = 0; i < coef.length; i++) {
            suma += Character.getNumericValue(ruc9.charAt(i)) * coef[i];
        }
        int verificador = 11 - (suma % 11);
        if (verificador == 11) verificador = 0;
        return verificador == Character.getNumericValue(ruc9.charAt(8));
    }

    private static boolean validarSociedadPrivada(String ruc10) {
        int[] coef = {4, 3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;
        for (int i = 0; i < coef.length; i++) {
            suma += Character.getNumericValue(ruc10.charAt(i)) * coef[i];
        }
        int verificador = 11 - (suma % 11);
        if (verificador == 11) verificador = 0;
        return verificador == Character.getNumericValue(ruc10.charAt(9));
    }
}

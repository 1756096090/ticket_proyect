package com.tuempresa.ticket_proyect.run;

import org.openxava.util.*;

/**
 * Ejecuta esta clase para arrancar la aplicación.
 *
 * Con OpenXava Studio/Eclipse: Botón derecho del ratón > Run As > Java Application
 */

public class ticket_proyect {

	public static void main(String[] args) throws Exception {
		DBServer.start("ticket_proyect-db"); // Para usar tu propia base de datos comenta esta línea y configura src/main/webapp/META-INF/context.xml
		AppServer.run("ticket_proyect"); // Usa AppServer.run("") para funcionar en el contexto raíz
	}

}

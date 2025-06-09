package com.tuempresa.ticket_proyect.calculadores;

import java.util.*;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

import com.tuempresa.ticket_proyect.modelo.*;

/**
 * Calculador que asigna automáticamente un usuario para la nueva asignación
 * basándose en la proporción inversa de tickets gestionados en la última semana.
 */
public class AsignadorProporcionalCalculator implements ICalculator {

    @Override
    public Object calculate() throws Exception {
        EntityManager em = XPersistence.getManager();
        
        // Fechas: ahora y hace 7 días
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date weekAgo = cal.getTime();

        // Contar asignaciones por usuario en la última semana
        List<Object[]> results = em.createQuery(
            "select a.usuario.idUsuario, count(a) " +
            "from Asignacion a " +
            "where a.fechaAsignacion between :start and :end " +
            "group by a.usuario.idUsuario", Object[].class)
            .setParameter("start", weekAgo)
            .setParameter("end", now)
            .getResultList();

        Map<Long, Long> counts = new HashMap<>();
        long total = 0;
        for (Object[] row : results) {
            Long userId = (Long) row[0];
            Long count = (Long) row[1];
            counts.put(userId, count);
            total += count;
        }

        // Obtener todos los usuarios activos
        List<Usuario> users = em.createQuery(
            "from Usuario u where u.activo = true", Usuario.class)
            .getResultList();

        // Calcular pesos: peso = total - conteo (quienes menos tickets reciban más peso)
        Map<Usuario, Long> weights = new LinkedHashMap<>();
        long sumWeights = 0;
        for (Usuario u : users) {
            long c = counts.getOrDefault(u.getIdUsuario(), 0L);
            long w = total - c;
            weights.put(u, w);
            sumWeights += w;
        }

        // Si no hay pesos, devolver primer usuario
        if (sumWeights <= 0 || users.isEmpty()) {
            return users.isEmpty() ? null : users.get(0).getIdUsuario();
        }

        // Selección aleatoria ponderada
        long rand = (long)(Math.random() * sumWeights);
        long cumulative = 0;
        for (Map.Entry<Usuario, Long> entry : weights.entrySet()) {
            cumulative += entry.getValue();
            if (rand < cumulative) {
                return entry.getKey().getIdUsuario();
            }
        }
        // Por defecto, el último
        return users.get(users.size() - 1).getIdUsuario();
    }


}

package com.tuempresa.ticket_proyect.modelo;

import javax.persistence.*;

public class TarifaService {

    private static EntityManagerFactory emf;

    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("default"); // Asegúrate del nombre correcto
            } catch (Exception e) {
                System.err.println("Error inicializando EntityManagerFactory: " + e.getMessage());
                throw e;
            }
        }
        return emf;
    }

    public static TarifaEspecialidad buscarPorEspecialidad(Especialidad especialidad) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("from TarifaEspecialidad t where t.especialidad = :esp", TarifaEspecialidad.class)
                .setParameter("esp", especialidad)
                .setMaxResults(1)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static TarifaTipoUrgencia buscarPorUrgencia(TipoUrgencia urgencia) {
        EntityManager em = getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("from TarifaTipoUrgencia t where t.tipoUrgencia = :urg", TarifaTipoUrgencia.class)
                .setParameter("urg", urgencia)
                .setMaxResults(1)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}

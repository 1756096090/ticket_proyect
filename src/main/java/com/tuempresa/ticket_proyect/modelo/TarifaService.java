package com.tuempresa.ticket_proyect.modelo;

import javax.persistence.*;


public class TarifaService {

    private static final EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("default"); // Usa tu unidad persistencia

    public static TarifaEspecialidad buscarPorEspecialidad(Especialidad especialidad) {
        EntityManager em = emf.createEntityManager();
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
        EntityManager em = emf.createEntityManager();
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

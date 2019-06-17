/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Processo;
import ENTIDADES.Processoaudiencia;
import ENTIDADES.Audiencia;
import CONTROLLER.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Julio
 */
public class ProcessoaudienciaJpaController implements Serializable {

    public ProcessoaudienciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Processoaudiencia processoaudiencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Audiencia processoaudienciaaudiencia = processoaudiencia.getProcessoaudienciaaudiencia();
            if (processoaudienciaaudiencia != null) {
                processoaudienciaaudiencia = em.getReference(processoaudienciaaudiencia.getClass(), processoaudienciaaudiencia.getId());
                processoaudiencia.setProcessoaudienciaaudiencia(processoaudienciaaudiencia);
            }
            Processo processoaudienciaprocesso = processoaudiencia.getProcessoaudienciaprocesso();
            if (processoaudienciaprocesso != null) {
                processoaudienciaprocesso = em.getReference(processoaudienciaprocesso.getClass(), processoaudienciaprocesso.getId());
                processoaudiencia.setProcessoaudienciaprocesso(processoaudienciaprocesso);
            }
            em.persist(processoaudiencia);
            if (processoaudienciaaudiencia != null) {
                processoaudienciaaudiencia.getProcessoaudienciaCollection().add(processoaudiencia);
                processoaudienciaaudiencia = em.merge(processoaudienciaaudiencia);
            }
            if (processoaudienciaprocesso != null) {
                processoaudienciaprocesso.getProcessoaudienciaCollection().add(processoaudiencia);
                processoaudienciaprocesso = em.merge(processoaudienciaprocesso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processoaudiencia processoaudiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processoaudiencia persistentProcessoaudiencia = em.find(Processoaudiencia.class, processoaudiencia.getId());
            Audiencia processoaudienciaaudienciaOld = persistentProcessoaudiencia.getProcessoaudienciaaudiencia();
            Audiencia processoaudienciaaudienciaNew = processoaudiencia.getProcessoaudienciaaudiencia();
            Processo processoaudienciaprocessoOld = persistentProcessoaudiencia.getProcessoaudienciaprocesso();
            Processo processoaudienciaprocessoNew = processoaudiencia.getProcessoaudienciaprocesso();
            if (processoaudienciaaudienciaNew != null) {
                processoaudienciaaudienciaNew = em.getReference(processoaudienciaaudienciaNew.getClass(), processoaudienciaaudienciaNew.getId());
                processoaudiencia.setProcessoaudienciaaudiencia(processoaudienciaaudienciaNew);
            }
            if (processoaudienciaprocessoNew != null) {
                processoaudienciaprocessoNew = em.getReference(processoaudienciaprocessoNew.getClass(), processoaudienciaprocessoNew.getId());
                processoaudiencia.setProcessoaudienciaprocesso(processoaudienciaprocessoNew);
            }
            processoaudiencia = em.merge(processoaudiencia);
            if (processoaudienciaaudienciaOld != null && !processoaudienciaaudienciaOld.equals(processoaudienciaaudienciaNew)) {
                processoaudienciaaudienciaOld.getProcessoaudienciaCollection().remove(processoaudiencia);
                processoaudienciaaudienciaOld = em.merge(processoaudienciaaudienciaOld);
            }
            if (processoaudienciaaudienciaNew != null && !processoaudienciaaudienciaNew.equals(processoaudienciaaudienciaOld)) {
                processoaudienciaaudienciaNew.getProcessoaudienciaCollection().add(processoaudiencia);
                processoaudienciaaudienciaNew = em.merge(processoaudienciaaudienciaNew);
            }
            if (processoaudienciaprocessoOld != null && !processoaudienciaprocessoOld.equals(processoaudienciaprocessoNew)) {
                processoaudienciaprocessoOld.getProcessoaudienciaCollection().remove(processoaudiencia);
                processoaudienciaprocessoOld = em.merge(processoaudienciaprocessoOld);
            }
            if (processoaudienciaprocessoNew != null && !processoaudienciaprocessoNew.equals(processoaudienciaprocessoOld)) {
                processoaudienciaprocessoNew.getProcessoaudienciaCollection().add(processoaudiencia);
                processoaudienciaprocessoNew = em.merge(processoaudienciaprocessoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processoaudiencia.getId();
                if (findProcessoaudiencia(id) == null) {
                    throw new NonexistentEntityException("The processoaudiencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processoaudiencia processoaudiencia;
            try {
                processoaudiencia = em.getReference(Processoaudiencia.class, id);
                processoaudiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processoaudiencia with id " + id + " no longer exists.", enfe);
            }
            Audiencia processoaudienciaaudiencia = processoaudiencia.getProcessoaudienciaaudiencia();
            if (processoaudienciaaudiencia != null) {
                processoaudienciaaudiencia.getProcessoaudienciaCollection().remove(processoaudiencia);
                processoaudienciaaudiencia = em.merge(processoaudienciaaudiencia);
            }
            Processo processoaudienciaprocesso = processoaudiencia.getProcessoaudienciaprocesso();
            if (processoaudienciaprocesso != null) {
                processoaudienciaprocesso.getProcessoaudienciaCollection().remove(processoaudiencia);
                processoaudienciaprocesso = em.merge(processoaudienciaprocesso);
            }
            em.remove(processoaudiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Processoaudiencia> findProcessoaudienciaEntities() {
        return findProcessoaudienciaEntities(true, -1, -1);
    }

    public List<Processoaudiencia> findProcessoaudienciaEntities(int maxResults, int firstResult) {
        return findProcessoaudienciaEntities(false, maxResults, firstResult);
    }

    private List<Processoaudiencia> findProcessoaudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Processoaudiencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Processoaudiencia findProcessoaudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Processoaudiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoaudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Processoaudiencia> rt = cq.from(Processoaudiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Relatoriodejuros;
import ENTIDADES.Peticaotabeladejuros;
import ENTIDADES.Peticao;
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
public class PeticaotabeladejurosJpaController implements Serializable {

    public PeticaotabeladejurosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peticaotabeladejuros peticaotabeladejuros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticao peticaotabeladejurospeticao = peticaotabeladejuros.getPeticaotabeladejurospeticao();
            if (peticaotabeladejurospeticao != null) {
                peticaotabeladejurospeticao = em.getReference(peticaotabeladejurospeticao.getClass(), peticaotabeladejurospeticao.getId());
                peticaotabeladejuros.setPeticaotabeladejurospeticao(peticaotabeladejurospeticao);
            }
            Relatoriodejuros peticaotabeladejurostabeladejuros = peticaotabeladejuros.getPeticaotabeladejurostabeladejuros();
            if (peticaotabeladejurostabeladejuros != null) {
                peticaotabeladejurostabeladejuros = em.getReference(peticaotabeladejurostabeladejuros.getClass(), peticaotabeladejurostabeladejuros.getId());
                peticaotabeladejuros.setPeticaotabeladejurostabeladejuros(peticaotabeladejurostabeladejuros);
            }
            em.persist(peticaotabeladejuros);
            if (peticaotabeladejurospeticao != null) {
                peticaotabeladejurospeticao.getPeticaotabeladejurosCollection().add(peticaotabeladejuros);
                peticaotabeladejurospeticao = em.merge(peticaotabeladejurospeticao);
            }
            if (peticaotabeladejurostabeladejuros != null) {
                peticaotabeladejurostabeladejuros.getPeticaotabeladejurosCollection().add(peticaotabeladejuros);
                peticaotabeladejurostabeladejuros = em.merge(peticaotabeladejurostabeladejuros);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peticaotabeladejuros peticaotabeladejuros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticaotabeladejuros persistentPeticaotabeladejuros = em.find(Peticaotabeladejuros.class, peticaotabeladejuros.getId());
            Peticao peticaotabeladejurospeticaoOld = persistentPeticaotabeladejuros.getPeticaotabeladejurospeticao();
            Peticao peticaotabeladejurospeticaoNew = peticaotabeladejuros.getPeticaotabeladejurospeticao();
            Relatoriodejuros peticaotabeladejurostabeladejurosOld = persistentPeticaotabeladejuros.getPeticaotabeladejurostabeladejuros();
            Relatoriodejuros peticaotabeladejurostabeladejurosNew = peticaotabeladejuros.getPeticaotabeladejurostabeladejuros();
            if (peticaotabeladejurospeticaoNew != null) {
                peticaotabeladejurospeticaoNew = em.getReference(peticaotabeladejurospeticaoNew.getClass(), peticaotabeladejurospeticaoNew.getId());
                peticaotabeladejuros.setPeticaotabeladejurospeticao(peticaotabeladejurospeticaoNew);
            }
            if (peticaotabeladejurostabeladejurosNew != null) {
                peticaotabeladejurostabeladejurosNew = em.getReference(peticaotabeladejurostabeladejurosNew.getClass(), peticaotabeladejurostabeladejurosNew.getId());
                peticaotabeladejuros.setPeticaotabeladejurostabeladejuros(peticaotabeladejurostabeladejurosNew);
            }
            peticaotabeladejuros = em.merge(peticaotabeladejuros);
            if (peticaotabeladejurospeticaoOld != null && !peticaotabeladejurospeticaoOld.equals(peticaotabeladejurospeticaoNew)) {
                peticaotabeladejurospeticaoOld.getPeticaotabeladejurosCollection().remove(peticaotabeladejuros);
                peticaotabeladejurospeticaoOld = em.merge(peticaotabeladejurospeticaoOld);
            }
            if (peticaotabeladejurospeticaoNew != null && !peticaotabeladejurospeticaoNew.equals(peticaotabeladejurospeticaoOld)) {
                peticaotabeladejurospeticaoNew.getPeticaotabeladejurosCollection().add(peticaotabeladejuros);
                peticaotabeladejurospeticaoNew = em.merge(peticaotabeladejurospeticaoNew);
            }
            if (peticaotabeladejurostabeladejurosOld != null && !peticaotabeladejurostabeladejurosOld.equals(peticaotabeladejurostabeladejurosNew)) {
                peticaotabeladejurostabeladejurosOld.getPeticaotabeladejurosCollection().remove(peticaotabeladejuros);
                peticaotabeladejurostabeladejurosOld = em.merge(peticaotabeladejurostabeladejurosOld);
            }
            if (peticaotabeladejurostabeladejurosNew != null && !peticaotabeladejurostabeladejurosNew.equals(peticaotabeladejurostabeladejurosOld)) {
                peticaotabeladejurostabeladejurosNew.getPeticaotabeladejurosCollection().add(peticaotabeladejuros);
                peticaotabeladejurostabeladejurosNew = em.merge(peticaotabeladejurostabeladejurosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = peticaotabeladejuros.getId();
                if (findPeticaotabeladejuros(id) == null) {
                    throw new NonexistentEntityException("The peticaotabeladejuros with id " + id + " no longer exists.");
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
            Peticaotabeladejuros peticaotabeladejuros;
            try {
                peticaotabeladejuros = em.getReference(Peticaotabeladejuros.class, id);
                peticaotabeladejuros.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peticaotabeladejuros with id " + id + " no longer exists.", enfe);
            }
            Peticao peticaotabeladejurospeticao = peticaotabeladejuros.getPeticaotabeladejurospeticao();
            if (peticaotabeladejurospeticao != null) {
                peticaotabeladejurospeticao.getPeticaotabeladejurosCollection().remove(peticaotabeladejuros);
                peticaotabeladejurospeticao = em.merge(peticaotabeladejurospeticao);
            }
            Relatoriodejuros peticaotabeladejurostabeladejuros = peticaotabeladejuros.getPeticaotabeladejurostabeladejuros();
            if (peticaotabeladejurostabeladejuros != null) {
                peticaotabeladejurostabeladejuros.getPeticaotabeladejurosCollection().remove(peticaotabeladejuros);
                peticaotabeladejurostabeladejuros = em.merge(peticaotabeladejurostabeladejuros);
            }
            em.remove(peticaotabeladejuros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peticaotabeladejuros> findPeticaotabeladejurosEntities() {
        return findPeticaotabeladejurosEntities(true, -1, -1);
    }

    public List<Peticaotabeladejuros> findPeticaotabeladejurosEntities(int maxResults, int firstResult) {
        return findPeticaotabeladejurosEntities(false, maxResults, firstResult);
    }

    private List<Peticaotabeladejuros> findPeticaotabeladejurosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peticaotabeladejuros.class));
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

    public Peticaotabeladejuros findPeticaotabeladejuros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peticaotabeladejuros.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeticaotabeladejurosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peticaotabeladejuros> rt = cq.from(Peticaotabeladejuros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

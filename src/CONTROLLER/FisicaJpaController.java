/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Pessoa;
import ENTIDADES.Fisica;
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
public class FisicaJpaController implements Serializable {

    public FisicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fisica fisica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa fisicapessoa = fisica.getFisicapessoa();
            if (fisicapessoa != null) {
                fisicapessoa = em.getReference(fisicapessoa.getClass(), fisicapessoa.getId());
                fisica.setFisicapessoa(fisicapessoa);
            }
            em.persist(fisica);
            if (fisicapessoa != null) {
                fisicapessoa.getFisicaCollection().add(fisica);
                fisicapessoa = em.merge(fisicapessoa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fisica fisica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fisica persistentFisica = em.find(Fisica.class, fisica.getId());
            Pessoa fisicapessoaOld = persistentFisica.getFisicapessoa();
            Pessoa fisicapessoaNew = fisica.getFisicapessoa();
            if (fisicapessoaNew != null) {
                fisicapessoaNew = em.getReference(fisicapessoaNew.getClass(), fisicapessoaNew.getId());
                fisica.setFisicapessoa(fisicapessoaNew);
            }
            fisica = em.merge(fisica);
            if (fisicapessoaOld != null && !fisicapessoaOld.equals(fisicapessoaNew)) {
                fisicapessoaOld.getFisicaCollection().remove(fisica);
                fisicapessoaOld = em.merge(fisicapessoaOld);
            }
            if (fisicapessoaNew != null && !fisicapessoaNew.equals(fisicapessoaOld)) {
                fisicapessoaNew.getFisicaCollection().add(fisica);
                fisicapessoaNew = em.merge(fisicapessoaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fisica.getId();
                if (findFisica(id) == null) {
                    throw new NonexistentEntityException("The fisica with id " + id + " no longer exists.");
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
            Fisica fisica;
            try {
                fisica = em.getReference(Fisica.class, id);
                fisica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fisica with id " + id + " no longer exists.", enfe);
            }
            Pessoa fisicapessoa = fisica.getFisicapessoa();
            if (fisicapessoa != null) {
                fisicapessoa.getFisicaCollection().remove(fisica);
                fisicapessoa = em.merge(fisicapessoa);
            }
            em.remove(fisica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fisica> findFisicaEntities() {
        return findFisicaEntities(true, -1, -1);
    }

    public List<Fisica> findFisicaEntities(int maxResults, int firstResult) {
        return findFisicaEntities(false, maxResults, firstResult);
    }

    private List<Fisica> findFisicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fisica.class));
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

    public Fisica findFisica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fisica.class, id);
        } finally {
            em.close();
        }
    }

    public int getFisicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fisica> rt = cq.from(Fisica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

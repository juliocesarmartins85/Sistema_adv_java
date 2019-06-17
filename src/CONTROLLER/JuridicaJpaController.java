/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Juridica;
import ENTIDADES.Pessoa;
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
public class JuridicaJpaController implements Serializable {

    public JuridicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juridica juridica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa juridicapessoa = juridica.getJuridicapessoa();
            if (juridicapessoa != null) {
                juridicapessoa = em.getReference(juridicapessoa.getClass(), juridicapessoa.getId());
                juridica.setJuridicapessoa(juridicapessoa);
            }
            em.persist(juridica);
            if (juridicapessoa != null) {
                juridicapessoa.getJuridicaCollection().add(juridica);
                juridicapessoa = em.merge(juridicapessoa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juridica juridica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juridica persistentJuridica = em.find(Juridica.class, juridica.getId());
            Pessoa juridicapessoaOld = persistentJuridica.getJuridicapessoa();
            Pessoa juridicapessoaNew = juridica.getJuridicapessoa();
            if (juridicapessoaNew != null) {
                juridicapessoaNew = em.getReference(juridicapessoaNew.getClass(), juridicapessoaNew.getId());
                juridica.setJuridicapessoa(juridicapessoaNew);
            }
            juridica = em.merge(juridica);
            if (juridicapessoaOld != null && !juridicapessoaOld.equals(juridicapessoaNew)) {
                juridicapessoaOld.getJuridicaCollection().remove(juridica);
                juridicapessoaOld = em.merge(juridicapessoaOld);
            }
            if (juridicapessoaNew != null && !juridicapessoaNew.equals(juridicapessoaOld)) {
                juridicapessoaNew.getJuridicaCollection().add(juridica);
                juridicapessoaNew = em.merge(juridicapessoaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = juridica.getId();
                if (findJuridica(id) == null) {
                    throw new NonexistentEntityException("The juridica with id " + id + " no longer exists.");
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
            Juridica juridica;
            try {
                juridica = em.getReference(Juridica.class, id);
                juridica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juridica with id " + id + " no longer exists.", enfe);
            }
            Pessoa juridicapessoa = juridica.getJuridicapessoa();
            if (juridicapessoa != null) {
                juridicapessoa.getJuridicaCollection().remove(juridica);
                juridicapessoa = em.merge(juridicapessoa);
            }
            em.remove(juridica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juridica> findJuridicaEntities() {
        return findJuridicaEntities(true, -1, -1);
    }

    public List<Juridica> findJuridicaEntities(int maxResults, int firstResult) {
        return findJuridicaEntities(false, maxResults, firstResult);
    }

    private List<Juridica> findJuridicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juridica.class));
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

    public Juridica findJuridica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juridica.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuridicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juridica> rt = cq.from(Juridica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

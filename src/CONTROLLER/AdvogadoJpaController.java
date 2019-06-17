/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Advogado;
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
public class AdvogadoJpaController implements Serializable {

    public AdvogadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Advogado advogado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa advogadopessoa = advogado.getAdvogadopessoa();
            if (advogadopessoa != null) {
                advogadopessoa = em.getReference(advogadopessoa.getClass(), advogadopessoa.getId());
                advogado.setAdvogadopessoa(advogadopessoa);
            }
            em.persist(advogado);
            if (advogadopessoa != null) {
                advogadopessoa.getAdvogadoCollection().add(advogado);
                advogadopessoa = em.merge(advogadopessoa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Advogado advogado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advogado persistentAdvogado = em.find(Advogado.class, advogado.getId());
            Pessoa advogadopessoaOld = persistentAdvogado.getAdvogadopessoa();
            Pessoa advogadopessoaNew = advogado.getAdvogadopessoa();
            if (advogadopessoaNew != null) {
                advogadopessoaNew = em.getReference(advogadopessoaNew.getClass(), advogadopessoaNew.getId());
                advogado.setAdvogadopessoa(advogadopessoaNew);
            }
            advogado = em.merge(advogado);
            if (advogadopessoaOld != null && !advogadopessoaOld.equals(advogadopessoaNew)) {
                advogadopessoaOld.getAdvogadoCollection().remove(advogado);
                advogadopessoaOld = em.merge(advogadopessoaOld);
            }
            if (advogadopessoaNew != null && !advogadopessoaNew.equals(advogadopessoaOld)) {
                advogadopessoaNew.getAdvogadoCollection().add(advogado);
                advogadopessoaNew = em.merge(advogadopessoaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = advogado.getId();
                if (findAdvogado(id) == null) {
                    throw new NonexistentEntityException("The advogado with id " + id + " no longer exists.");
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
            Advogado advogado;
            try {
                advogado = em.getReference(Advogado.class, id);
                advogado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The advogado with id " + id + " no longer exists.", enfe);
            }
            Pessoa advogadopessoa = advogado.getAdvogadopessoa();
            if (advogadopessoa != null) {
                advogadopessoa.getAdvogadoCollection().remove(advogado);
                advogadopessoa = em.merge(advogadopessoa);
            }
            em.remove(advogado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Advogado> findAdvogadoEntities() {
        return findAdvogadoEntities(true, -1, -1);
    }

    public List<Advogado> findAdvogadoEntities(int maxResults, int firstResult) {
        return findAdvogadoEntities(false, maxResults, firstResult);
    }

    private List<Advogado> findAdvogadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Advogado.class));
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

    public Advogado findAdvogado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Advogado.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdvogadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Advogado> rt = cq.from(Advogado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

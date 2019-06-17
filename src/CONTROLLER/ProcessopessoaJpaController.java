/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Pessoa;
import ENTIDADES.Processopessoa;
import ENTIDADES.Processo;
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
public class ProcessopessoaJpaController implements Serializable {

    public ProcessopessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Processopessoa processopessoa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa processopessoapessoa = processopessoa.getProcessopessoapessoa();
            if (processopessoapessoa != null) {
                processopessoapessoa = em.getReference(processopessoapessoa.getClass(), processopessoapessoa.getId());
                processopessoa.setProcessopessoapessoa(processopessoapessoa);
            }
            Processo processopessoaprocesso = processopessoa.getProcessopessoaprocesso();
            if (processopessoaprocesso != null) {
                processopessoaprocesso = em.getReference(processopessoaprocesso.getClass(), processopessoaprocesso.getId());
                processopessoa.setProcessopessoaprocesso(processopessoaprocesso);
            }
            em.persist(processopessoa);
            if (processopessoapessoa != null) {
                processopessoapessoa.getProcessopessoaCollection().add(processopessoa);
                processopessoapessoa = em.merge(processopessoapessoa);
            }
            if (processopessoaprocesso != null) {
                processopessoaprocesso.getProcessopessoaCollection().add(processopessoa);
                processopessoaprocesso = em.merge(processopessoaprocesso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processopessoa processopessoa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processopessoa persistentProcessopessoa = em.find(Processopessoa.class, processopessoa.getId());
            Pessoa processopessoapessoaOld = persistentProcessopessoa.getProcessopessoapessoa();
            Pessoa processopessoapessoaNew = processopessoa.getProcessopessoapessoa();
            Processo processopessoaprocessoOld = persistentProcessopessoa.getProcessopessoaprocesso();
            Processo processopessoaprocessoNew = processopessoa.getProcessopessoaprocesso();
            if (processopessoapessoaNew != null) {
                processopessoapessoaNew = em.getReference(processopessoapessoaNew.getClass(), processopessoapessoaNew.getId());
                processopessoa.setProcessopessoapessoa(processopessoapessoaNew);
            }
            if (processopessoaprocessoNew != null) {
                processopessoaprocessoNew = em.getReference(processopessoaprocessoNew.getClass(), processopessoaprocessoNew.getId());
                processopessoa.setProcessopessoaprocesso(processopessoaprocessoNew);
            }
            processopessoa = em.merge(processopessoa);
            if (processopessoapessoaOld != null && !processopessoapessoaOld.equals(processopessoapessoaNew)) {
                processopessoapessoaOld.getProcessopessoaCollection().remove(processopessoa);
                processopessoapessoaOld = em.merge(processopessoapessoaOld);
            }
            if (processopessoapessoaNew != null && !processopessoapessoaNew.equals(processopessoapessoaOld)) {
                processopessoapessoaNew.getProcessopessoaCollection().add(processopessoa);
                processopessoapessoaNew = em.merge(processopessoapessoaNew);
            }
            if (processopessoaprocessoOld != null && !processopessoaprocessoOld.equals(processopessoaprocessoNew)) {
                processopessoaprocessoOld.getProcessopessoaCollection().remove(processopessoa);
                processopessoaprocessoOld = em.merge(processopessoaprocessoOld);
            }
            if (processopessoaprocessoNew != null && !processopessoaprocessoNew.equals(processopessoaprocessoOld)) {
                processopessoaprocessoNew.getProcessopessoaCollection().add(processopessoa);
                processopessoaprocessoNew = em.merge(processopessoaprocessoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processopessoa.getId();
                if (findProcessopessoa(id) == null) {
                    throw new NonexistentEntityException("The processopessoa with id " + id + " no longer exists.");
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
            Processopessoa processopessoa;
            try {
                processopessoa = em.getReference(Processopessoa.class, id);
                processopessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processopessoa with id " + id + " no longer exists.", enfe);
            }
            Pessoa processopessoapessoa = processopessoa.getProcessopessoapessoa();
            if (processopessoapessoa != null) {
                processopessoapessoa.getProcessopessoaCollection().remove(processopessoa);
                processopessoapessoa = em.merge(processopessoapessoa);
            }
            Processo processopessoaprocesso = processopessoa.getProcessopessoaprocesso();
            if (processopessoaprocesso != null) {
                processopessoaprocesso.getProcessopessoaCollection().remove(processopessoa);
                processopessoaprocesso = em.merge(processopessoaprocesso);
            }
            em.remove(processopessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Processopessoa> findProcessopessoaEntities() {
        return findProcessopessoaEntities(true, -1, -1);
    }

    public List<Processopessoa> findProcessopessoaEntities(int maxResults, int firstResult) {
        return findProcessopessoaEntities(false, maxResults, firstResult);
    }

    private List<Processopessoa> findProcessopessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Processopessoa.class));
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

    public Processopessoa findProcessopessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Processopessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessopessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Processopessoa> rt = cq.from(Processopessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

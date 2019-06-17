/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Contrato;
import ENTIDADES.Pessoa;
import ENTIDADES.Contratopessoa;
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
public class ContratopessoaJpaController implements Serializable {

    public ContratopessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contratopessoa contratopessoa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contrato contratopessoacontrato = contratopessoa.getContratopessoacontrato();
            if (contratopessoacontrato != null) {
                contratopessoacontrato = em.getReference(contratopessoacontrato.getClass(), contratopessoacontrato.getId());
                contratopessoa.setContratopessoacontrato(contratopessoacontrato);
            }
            Pessoa contratopessoapessoa = contratopessoa.getContratopessoapessoa();
            if (contratopessoapessoa != null) {
                contratopessoapessoa = em.getReference(contratopessoapessoa.getClass(), contratopessoapessoa.getId());
                contratopessoa.setContratopessoapessoa(contratopessoapessoa);
            }
            em.persist(contratopessoa);
            if (contratopessoacontrato != null) {
                contratopessoacontrato.getContratopessoaCollection().add(contratopessoa);
                contratopessoacontrato = em.merge(contratopessoacontrato);
            }
            if (contratopessoapessoa != null) {
                contratopessoapessoa.getContratopessoaCollection().add(contratopessoa);
                contratopessoapessoa = em.merge(contratopessoapessoa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contratopessoa contratopessoa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contratopessoa persistentContratopessoa = em.find(Contratopessoa.class, contratopessoa.getId());
            Contrato contratopessoacontratoOld = persistentContratopessoa.getContratopessoacontrato();
            Contrato contratopessoacontratoNew = contratopessoa.getContratopessoacontrato();
            Pessoa contratopessoapessoaOld = persistentContratopessoa.getContratopessoapessoa();
            Pessoa contratopessoapessoaNew = contratopessoa.getContratopessoapessoa();
            if (contratopessoacontratoNew != null) {
                contratopessoacontratoNew = em.getReference(contratopessoacontratoNew.getClass(), contratopessoacontratoNew.getId());
                contratopessoa.setContratopessoacontrato(contratopessoacontratoNew);
            }
            if (contratopessoapessoaNew != null) {
                contratopessoapessoaNew = em.getReference(contratopessoapessoaNew.getClass(), contratopessoapessoaNew.getId());
                contratopessoa.setContratopessoapessoa(contratopessoapessoaNew);
            }
            contratopessoa = em.merge(contratopessoa);
            if (contratopessoacontratoOld != null && !contratopessoacontratoOld.equals(contratopessoacontratoNew)) {
                contratopessoacontratoOld.getContratopessoaCollection().remove(contratopessoa);
                contratopessoacontratoOld = em.merge(contratopessoacontratoOld);
            }
            if (contratopessoacontratoNew != null && !contratopessoacontratoNew.equals(contratopessoacontratoOld)) {
                contratopessoacontratoNew.getContratopessoaCollection().add(contratopessoa);
                contratopessoacontratoNew = em.merge(contratopessoacontratoNew);
            }
            if (contratopessoapessoaOld != null && !contratopessoapessoaOld.equals(contratopessoapessoaNew)) {
                contratopessoapessoaOld.getContratopessoaCollection().remove(contratopessoa);
                contratopessoapessoaOld = em.merge(contratopessoapessoaOld);
            }
            if (contratopessoapessoaNew != null && !contratopessoapessoaNew.equals(contratopessoapessoaOld)) {
                contratopessoapessoaNew.getContratopessoaCollection().add(contratopessoa);
                contratopessoapessoaNew = em.merge(contratopessoapessoaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratopessoa.getId();
                if (findContratopessoa(id) == null) {
                    throw new NonexistentEntityException("The contratopessoa with id " + id + " no longer exists.");
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
            Contratopessoa contratopessoa;
            try {
                contratopessoa = em.getReference(Contratopessoa.class, id);
                contratopessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratopessoa with id " + id + " no longer exists.", enfe);
            }
            Contrato contratopessoacontrato = contratopessoa.getContratopessoacontrato();
            if (contratopessoacontrato != null) {
                contratopessoacontrato.getContratopessoaCollection().remove(contratopessoa);
                contratopessoacontrato = em.merge(contratopessoacontrato);
            }
            Pessoa contratopessoapessoa = contratopessoa.getContratopessoapessoa();
            if (contratopessoapessoa != null) {
                contratopessoapessoa.getContratopessoaCollection().remove(contratopessoa);
                contratopessoapessoa = em.merge(contratopessoapessoa);
            }
            em.remove(contratopessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contratopessoa> findContratopessoaEntities() {
        return findContratopessoaEntities(true, -1, -1);
    }

    public List<Contratopessoa> findContratopessoaEntities(int maxResults, int firstResult) {
        return findContratopessoaEntities(false, maxResults, firstResult);
    }

    private List<Contratopessoa> findContratopessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contratopessoa.class));
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

    public Contratopessoa findContratopessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contratopessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratopessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contratopessoa> rt = cq.from(Contratopessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Peticaopessoa;
import ENTIDADES.Pessoa;
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
public class PeticaopessoaJpaController implements Serializable {

    public PeticaopessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peticaopessoa peticaopessoa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa peticaopessoapesssoa = peticaopessoa.getPeticaopessoapesssoa();
            if (peticaopessoapesssoa != null) {
                peticaopessoapesssoa = em.getReference(peticaopessoapesssoa.getClass(), peticaopessoapesssoa.getId());
                peticaopessoa.setPeticaopessoapesssoa(peticaopessoapesssoa);
            }
            Peticao peticaopessoapeticao = peticaopessoa.getPeticaopessoapeticao();
            if (peticaopessoapeticao != null) {
                peticaopessoapeticao = em.getReference(peticaopessoapeticao.getClass(), peticaopessoapeticao.getId());
                peticaopessoa.setPeticaopessoapeticao(peticaopessoapeticao);
            }
            em.persist(peticaopessoa);
            if (peticaopessoapesssoa != null) {
                peticaopessoapesssoa.getPeticaopessoaCollection().add(peticaopessoa);
                peticaopessoapesssoa = em.merge(peticaopessoapesssoa);
            }
            if (peticaopessoapeticao != null) {
                peticaopessoapeticao.getPeticaopessoaCollection().add(peticaopessoa);
                peticaopessoapeticao = em.merge(peticaopessoapeticao);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peticaopessoa peticaopessoa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticaopessoa persistentPeticaopessoa = em.find(Peticaopessoa.class, peticaopessoa.getId());
            Pessoa peticaopessoapesssoaOld = persistentPeticaopessoa.getPeticaopessoapesssoa();
            Pessoa peticaopessoapesssoaNew = peticaopessoa.getPeticaopessoapesssoa();
            Peticao peticaopessoapeticaoOld = persistentPeticaopessoa.getPeticaopessoapeticao();
            Peticao peticaopessoapeticaoNew = peticaopessoa.getPeticaopessoapeticao();
            if (peticaopessoapesssoaNew != null) {
                peticaopessoapesssoaNew = em.getReference(peticaopessoapesssoaNew.getClass(), peticaopessoapesssoaNew.getId());
                peticaopessoa.setPeticaopessoapesssoa(peticaopessoapesssoaNew);
            }
            if (peticaopessoapeticaoNew != null) {
                peticaopessoapeticaoNew = em.getReference(peticaopessoapeticaoNew.getClass(), peticaopessoapeticaoNew.getId());
                peticaopessoa.setPeticaopessoapeticao(peticaopessoapeticaoNew);
            }
            peticaopessoa = em.merge(peticaopessoa);
            if (peticaopessoapesssoaOld != null && !peticaopessoapesssoaOld.equals(peticaopessoapesssoaNew)) {
                peticaopessoapesssoaOld.getPeticaopessoaCollection().remove(peticaopessoa);
                peticaopessoapesssoaOld = em.merge(peticaopessoapesssoaOld);
            }
            if (peticaopessoapesssoaNew != null && !peticaopessoapesssoaNew.equals(peticaopessoapesssoaOld)) {
                peticaopessoapesssoaNew.getPeticaopessoaCollection().add(peticaopessoa);
                peticaopessoapesssoaNew = em.merge(peticaopessoapesssoaNew);
            }
            if (peticaopessoapeticaoOld != null && !peticaopessoapeticaoOld.equals(peticaopessoapeticaoNew)) {
                peticaopessoapeticaoOld.getPeticaopessoaCollection().remove(peticaopessoa);
                peticaopessoapeticaoOld = em.merge(peticaopessoapeticaoOld);
            }
            if (peticaopessoapeticaoNew != null && !peticaopessoapeticaoNew.equals(peticaopessoapeticaoOld)) {
                peticaopessoapeticaoNew.getPeticaopessoaCollection().add(peticaopessoa);
                peticaopessoapeticaoNew = em.merge(peticaopessoapeticaoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = peticaopessoa.getId();
                if (findPeticaopessoa(id) == null) {
                    throw new NonexistentEntityException("The peticaopessoa with id " + id + " no longer exists.");
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
            Peticaopessoa peticaopessoa;
            try {
                peticaopessoa = em.getReference(Peticaopessoa.class, id);
                peticaopessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peticaopessoa with id " + id + " no longer exists.", enfe);
            }
            Pessoa peticaopessoapesssoa = peticaopessoa.getPeticaopessoapesssoa();
            if (peticaopessoapesssoa != null) {
                peticaopessoapesssoa.getPeticaopessoaCollection().remove(peticaopessoa);
                peticaopessoapesssoa = em.merge(peticaopessoapesssoa);
            }
            Peticao peticaopessoapeticao = peticaopessoa.getPeticaopessoapeticao();
            if (peticaopessoapeticao != null) {
                peticaopessoapeticao.getPeticaopessoaCollection().remove(peticaopessoa);
                peticaopessoapeticao = em.merge(peticaopessoapeticao);
            }
            em.remove(peticaopessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peticaopessoa> findPeticaopessoaEntities() {
        return findPeticaopessoaEntities(true, -1, -1);
    }

    public List<Peticaopessoa> findPeticaopessoaEntities(int maxResults, int firstResult) {
        return findPeticaopessoaEntities(false, maxResults, firstResult);
    }

    private List<Peticaopessoa> findPeticaopessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peticaopessoa.class));
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

    public Peticaopessoa findPeticaopessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peticaopessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeticaopessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peticaopessoa> rt = cq.from(Peticaopessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

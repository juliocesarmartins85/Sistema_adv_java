/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Procuracao;
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
public class ProcuracaoJpaController implements Serializable {

    public ProcuracaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procuracao procuracao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa procuracaopessoa = procuracao.getProcuracaopessoa();
            if (procuracaopessoa != null) {
                procuracaopessoa = em.getReference(procuracaopessoa.getClass(), procuracaopessoa.getId());
                procuracao.setProcuracaopessoa(procuracaopessoa);
            }
            em.persist(procuracao);
            if (procuracaopessoa != null) {
                procuracaopessoa.getProcuracaoCollection().add(procuracao);
                procuracaopessoa = em.merge(procuracaopessoa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procuracao procuracao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procuracao persistentProcuracao = em.find(Procuracao.class, procuracao.getId());
            Pessoa procuracaopessoaOld = persistentProcuracao.getProcuracaopessoa();
            Pessoa procuracaopessoaNew = procuracao.getProcuracaopessoa();
            if (procuracaopessoaNew != null) {
                procuracaopessoaNew = em.getReference(procuracaopessoaNew.getClass(), procuracaopessoaNew.getId());
                procuracao.setProcuracaopessoa(procuracaopessoaNew);
            }
            procuracao = em.merge(procuracao);
            if (procuracaopessoaOld != null && !procuracaopessoaOld.equals(procuracaopessoaNew)) {
                procuracaopessoaOld.getProcuracaoCollection().remove(procuracao);
                procuracaopessoaOld = em.merge(procuracaopessoaOld);
            }
            if (procuracaopessoaNew != null && !procuracaopessoaNew.equals(procuracaopessoaOld)) {
                procuracaopessoaNew.getProcuracaoCollection().add(procuracao);
                procuracaopessoaNew = em.merge(procuracaopessoaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = procuracao.getId();
                if (findProcuracao(id) == null) {
                    throw new NonexistentEntityException("The procuracao with id " + id + " no longer exists.");
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
            Procuracao procuracao;
            try {
                procuracao = em.getReference(Procuracao.class, id);
                procuracao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procuracao with id " + id + " no longer exists.", enfe);
            }
            Pessoa procuracaopessoa = procuracao.getProcuracaopessoa();
            if (procuracaopessoa != null) {
                procuracaopessoa.getProcuracaoCollection().remove(procuracao);
                procuracaopessoa = em.merge(procuracaopessoa);
            }
            em.remove(procuracao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procuracao> findProcuracaoEntities() {
        return findProcuracaoEntities(true, -1, -1);
    }

    public List<Procuracao> findProcuracaoEntities(int maxResults, int firstResult) {
        return findProcuracaoEntities(false, maxResults, firstResult);
    }

    private List<Procuracao> findProcuracaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procuracao.class));
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

    public Procuracao findProcuracao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procuracao.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcuracaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procuracao> rt = cq.from(Procuracao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

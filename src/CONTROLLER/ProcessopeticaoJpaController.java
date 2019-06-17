/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Processopeticao;
import ENTIDADES.Processo;
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
public class ProcessopeticaoJpaController implements Serializable {

    public ProcessopeticaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Processopeticao processopeticao) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticao processopeticaopeticao = processopeticao.getProcessopeticaopeticao();
            if (processopeticaopeticao != null) {
                processopeticaopeticao = em.getReference(processopeticaopeticao.getClass(), processopeticaopeticao.getId());
                processopeticao.setProcessopeticaopeticao(processopeticaopeticao);
            }
            Processo processopeticaoprocesso = processopeticao.getProcessopeticaoprocesso();
            if (processopeticaoprocesso != null) {
                processopeticaoprocesso = em.getReference(processopeticaoprocesso.getClass(), processopeticaoprocesso.getId());
                processopeticao.setProcessopeticaoprocesso(processopeticaoprocesso);
            }
            em.persist(processopeticao);
            if (processopeticaopeticao != null) {
                processopeticaopeticao.getProcessopeticaoCollection().add(processopeticao);
                processopeticaopeticao = em.merge(processopeticaopeticao);
            }
            if (processopeticaoprocesso != null) {
                processopeticaoprocesso.getProcessopeticaoCollection().add(processopeticao);
                processopeticaoprocesso = em.merge(processopeticaoprocesso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processopeticao processopeticao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processopeticao persistentProcessopeticao = em.find(Processopeticao.class, processopeticao.getId());
            Peticao processopeticaopeticaoOld = persistentProcessopeticao.getProcessopeticaopeticao();
            Peticao processopeticaopeticaoNew = processopeticao.getProcessopeticaopeticao();
            Processo processopeticaoprocessoOld = persistentProcessopeticao.getProcessopeticaoprocesso();
            Processo processopeticaoprocessoNew = processopeticao.getProcessopeticaoprocesso();
            if (processopeticaopeticaoNew != null) {
                processopeticaopeticaoNew = em.getReference(processopeticaopeticaoNew.getClass(), processopeticaopeticaoNew.getId());
                processopeticao.setProcessopeticaopeticao(processopeticaopeticaoNew);
            }
            if (processopeticaoprocessoNew != null) {
                processopeticaoprocessoNew = em.getReference(processopeticaoprocessoNew.getClass(), processopeticaoprocessoNew.getId());
                processopeticao.setProcessopeticaoprocesso(processopeticaoprocessoNew);
            }
            processopeticao = em.merge(processopeticao);
            if (processopeticaopeticaoOld != null && !processopeticaopeticaoOld.equals(processopeticaopeticaoNew)) {
                processopeticaopeticaoOld.getProcessopeticaoCollection().remove(processopeticao);
                processopeticaopeticaoOld = em.merge(processopeticaopeticaoOld);
            }
            if (processopeticaopeticaoNew != null && !processopeticaopeticaoNew.equals(processopeticaopeticaoOld)) {
                processopeticaopeticaoNew.getProcessopeticaoCollection().add(processopeticao);
                processopeticaopeticaoNew = em.merge(processopeticaopeticaoNew);
            }
            if (processopeticaoprocessoOld != null && !processopeticaoprocessoOld.equals(processopeticaoprocessoNew)) {
                processopeticaoprocessoOld.getProcessopeticaoCollection().remove(processopeticao);
                processopeticaoprocessoOld = em.merge(processopeticaoprocessoOld);
            }
            if (processopeticaoprocessoNew != null && !processopeticaoprocessoNew.equals(processopeticaoprocessoOld)) {
                processopeticaoprocessoNew.getProcessopeticaoCollection().add(processopeticao);
                processopeticaoprocessoNew = em.merge(processopeticaoprocessoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processopeticao.getId();
                if (findProcessopeticao(id) == null) {
                    throw new NonexistentEntityException("The processopeticao with id " + id + " no longer exists.");
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
            Processopeticao processopeticao;
            try {
                processopeticao = em.getReference(Processopeticao.class, id);
                processopeticao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processopeticao with id " + id + " no longer exists.", enfe);
            }
            Peticao processopeticaopeticao = processopeticao.getProcessopeticaopeticao();
            if (processopeticaopeticao != null) {
                processopeticaopeticao.getProcessopeticaoCollection().remove(processopeticao);
                processopeticaopeticao = em.merge(processopeticaopeticao);
            }
            Processo processopeticaoprocesso = processopeticao.getProcessopeticaoprocesso();
            if (processopeticaoprocesso != null) {
                processopeticaoprocesso.getProcessopeticaoCollection().remove(processopeticao);
                processopeticaoprocesso = em.merge(processopeticaoprocesso);
            }
            em.remove(processopeticao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Processopeticao> findProcessopeticaoEntities() {
        return findProcessopeticaoEntities(true, -1, -1);
    }

    public List<Processopeticao> findProcessopeticaoEntities(int maxResults, int firstResult) {
        return findProcessopeticaoEntities(false, maxResults, firstResult);
    }

    private List<Processopeticao> findProcessopeticaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Processopeticao.class));
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

    public Processopeticao findProcessopeticao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Processopeticao.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessopeticaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Processopeticao> rt = cq.from(Processopeticao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

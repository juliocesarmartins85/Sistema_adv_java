/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Jurisprudencia;
import ENTIDADES.Processo;
import CONTROLLER.exceptions.IllegalOrphanException;
import CONTROLLER.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Julio
 */
public class JurisprudenciaJpaController implements Serializable {

    public JurisprudenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jurisprudencia jurisprudencia) {
        if (jurisprudencia.getProcessoCollection() == null) {
            jurisprudencia.setProcessoCollection(new ArrayList<Processo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Processo> attachedProcessoCollection = new ArrayList<Processo>();
            for (Processo processoCollectionProcessoToAttach : jurisprudencia.getProcessoCollection()) {
                processoCollectionProcessoToAttach = em.getReference(processoCollectionProcessoToAttach.getClass(), processoCollectionProcessoToAttach.getId());
                attachedProcessoCollection.add(processoCollectionProcessoToAttach);
            }
            jurisprudencia.setProcessoCollection(attachedProcessoCollection);
            em.persist(jurisprudencia);
            for (Processo processoCollectionProcesso : jurisprudencia.getProcessoCollection()) {
                Jurisprudencia oldProcessojurisprudenciaOfProcessoCollectionProcesso = processoCollectionProcesso.getProcessojurisprudencia();
                processoCollectionProcesso.setProcessojurisprudencia(jurisprudencia);
                processoCollectionProcesso = em.merge(processoCollectionProcesso);
                if (oldProcessojurisprudenciaOfProcessoCollectionProcesso != null) {
                    oldProcessojurisprudenciaOfProcessoCollectionProcesso.getProcessoCollection().remove(processoCollectionProcesso);
                    oldProcessojurisprudenciaOfProcessoCollectionProcesso = em.merge(oldProcessojurisprudenciaOfProcessoCollectionProcesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jurisprudencia jurisprudencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jurisprudencia persistentJurisprudencia = em.find(Jurisprudencia.class, jurisprudencia.getId());
            Collection<Processo> processoCollectionOld = persistentJurisprudencia.getProcessoCollection();
            Collection<Processo> processoCollectionNew = jurisprudencia.getProcessoCollection();
            List<String> illegalOrphanMessages = null;
            for (Processo processoCollectionOldProcesso : processoCollectionOld) {
                if (!processoCollectionNew.contains(processoCollectionOldProcesso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Processo " + processoCollectionOldProcesso + " since its processojurisprudencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Processo> attachedProcessoCollectionNew = new ArrayList<Processo>();
            for (Processo processoCollectionNewProcessoToAttach : processoCollectionNew) {
                processoCollectionNewProcessoToAttach = em.getReference(processoCollectionNewProcessoToAttach.getClass(), processoCollectionNewProcessoToAttach.getId());
                attachedProcessoCollectionNew.add(processoCollectionNewProcessoToAttach);
            }
            processoCollectionNew = attachedProcessoCollectionNew;
            jurisprudencia.setProcessoCollection(processoCollectionNew);
            jurisprudencia = em.merge(jurisprudencia);
            for (Processo processoCollectionNewProcesso : processoCollectionNew) {
                if (!processoCollectionOld.contains(processoCollectionNewProcesso)) {
                    Jurisprudencia oldProcessojurisprudenciaOfProcessoCollectionNewProcesso = processoCollectionNewProcesso.getProcessojurisprudencia();
                    processoCollectionNewProcesso.setProcessojurisprudencia(jurisprudencia);
                    processoCollectionNewProcesso = em.merge(processoCollectionNewProcesso);
                    if (oldProcessojurisprudenciaOfProcessoCollectionNewProcesso != null && !oldProcessojurisprudenciaOfProcessoCollectionNewProcesso.equals(jurisprudencia)) {
                        oldProcessojurisprudenciaOfProcessoCollectionNewProcesso.getProcessoCollection().remove(processoCollectionNewProcesso);
                        oldProcessojurisprudenciaOfProcessoCollectionNewProcesso = em.merge(oldProcessojurisprudenciaOfProcessoCollectionNewProcesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jurisprudencia.getId();
                if (findJurisprudencia(id) == null) {
                    throw new NonexistentEntityException("The jurisprudencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jurisprudencia jurisprudencia;
            try {
                jurisprudencia = em.getReference(Jurisprudencia.class, id);
                jurisprudencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jurisprudencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Processo> processoCollectionOrphanCheck = jurisprudencia.getProcessoCollection();
            for (Processo processoCollectionOrphanCheckProcesso : processoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jurisprudencia (" + jurisprudencia + ") cannot be destroyed since the Processo " + processoCollectionOrphanCheckProcesso + " in its processoCollection field has a non-nullable processojurisprudencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(jurisprudencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jurisprudencia> findJurisprudenciaEntities() {
        return findJurisprudenciaEntities(true, -1, -1);
    }

    public List<Jurisprudencia> findJurisprudenciaEntities(int maxResults, int firstResult) {
        return findJurisprudenciaEntities(false, maxResults, firstResult);
    }

    private List<Jurisprudencia> findJurisprudenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jurisprudencia.class));
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

    public Jurisprudencia findJurisprudencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jurisprudencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getJurisprudenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jurisprudencia> rt = cq.from(Jurisprudencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

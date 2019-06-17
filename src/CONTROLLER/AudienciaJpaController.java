/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Juiz;
import ENTIDADES.Forum;
import ENTIDADES.Processoaudiencia;
import ENTIDADES.Audiencia;
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
public class AudienciaJpaController implements Serializable {

    public AudienciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Audiencia audiencia) {
        if (audiencia.getProcessoaudienciaCollection() == null) {
            audiencia.setProcessoaudienciaCollection(new ArrayList<Processoaudiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Forum audienciaforum = audiencia.getAudienciaforum();
            if (audienciaforum != null) {
                audienciaforum = em.getReference(audienciaforum.getClass(), audienciaforum.getId());
                audiencia.setAudienciaforum(audienciaforum);
            }
            Juiz audienciajuiz = audiencia.getAudienciajuiz();
            if (audienciajuiz != null) {
                audienciajuiz = em.getReference(audienciajuiz.getClass(), audienciajuiz.getId());
                audiencia.setAudienciajuiz(audienciajuiz);
            }
            Collection<Processoaudiencia> attachedProcessoaudienciaCollection = new ArrayList<Processoaudiencia>();
            for (Processoaudiencia processoaudienciaCollectionProcessoaudienciaToAttach : audiencia.getProcessoaudienciaCollection()) {
                processoaudienciaCollectionProcessoaudienciaToAttach = em.getReference(processoaudienciaCollectionProcessoaudienciaToAttach.getClass(), processoaudienciaCollectionProcessoaudienciaToAttach.getId());
                attachedProcessoaudienciaCollection.add(processoaudienciaCollectionProcessoaudienciaToAttach);
            }
            audiencia.setProcessoaudienciaCollection(attachedProcessoaudienciaCollection);
            em.persist(audiencia);
            if (audienciaforum != null) {
                audienciaforum.getAudienciaCollection().add(audiencia);
                audienciaforum = em.merge(audienciaforum);
            }
            if (audienciajuiz != null) {
                audienciajuiz.getAudienciaCollection().add(audiencia);
                audienciajuiz = em.merge(audienciajuiz);
            }
            for (Processoaudiencia processoaudienciaCollectionProcessoaudiencia : audiencia.getProcessoaudienciaCollection()) {
                Audiencia oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionProcessoaudiencia = processoaudienciaCollectionProcessoaudiencia.getProcessoaudienciaaudiencia();
                processoaudienciaCollectionProcessoaudiencia.setProcessoaudienciaaudiencia(audiencia);
                processoaudienciaCollectionProcessoaudiencia = em.merge(processoaudienciaCollectionProcessoaudiencia);
                if (oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionProcessoaudiencia != null) {
                    oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionProcessoaudiencia.getProcessoaudienciaCollection().remove(processoaudienciaCollectionProcessoaudiencia);
                    oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionProcessoaudiencia = em.merge(oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionProcessoaudiencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Audiencia audiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Audiencia persistentAudiencia = em.find(Audiencia.class, audiencia.getId());
            Forum audienciaforumOld = persistentAudiencia.getAudienciaforum();
            Forum audienciaforumNew = audiencia.getAudienciaforum();
            Juiz audienciajuizOld = persistentAudiencia.getAudienciajuiz();
            Juiz audienciajuizNew = audiencia.getAudienciajuiz();
            Collection<Processoaudiencia> processoaudienciaCollectionOld = persistentAudiencia.getProcessoaudienciaCollection();
            Collection<Processoaudiencia> processoaudienciaCollectionNew = audiencia.getProcessoaudienciaCollection();
            if (audienciaforumNew != null) {
                audienciaforumNew = em.getReference(audienciaforumNew.getClass(), audienciaforumNew.getId());
                audiencia.setAudienciaforum(audienciaforumNew);
            }
            if (audienciajuizNew != null) {
                audienciajuizNew = em.getReference(audienciajuizNew.getClass(), audienciajuizNew.getId());
                audiencia.setAudienciajuiz(audienciajuizNew);
            }
            Collection<Processoaudiencia> attachedProcessoaudienciaCollectionNew = new ArrayList<Processoaudiencia>();
            for (Processoaudiencia processoaudienciaCollectionNewProcessoaudienciaToAttach : processoaudienciaCollectionNew) {
                processoaudienciaCollectionNewProcessoaudienciaToAttach = em.getReference(processoaudienciaCollectionNewProcessoaudienciaToAttach.getClass(), processoaudienciaCollectionNewProcessoaudienciaToAttach.getId());
                attachedProcessoaudienciaCollectionNew.add(processoaudienciaCollectionNewProcessoaudienciaToAttach);
            }
            processoaudienciaCollectionNew = attachedProcessoaudienciaCollectionNew;
            audiencia.setProcessoaudienciaCollection(processoaudienciaCollectionNew);
            audiencia = em.merge(audiencia);
            if (audienciaforumOld != null && !audienciaforumOld.equals(audienciaforumNew)) {
                audienciaforumOld.getAudienciaCollection().remove(audiencia);
                audienciaforumOld = em.merge(audienciaforumOld);
            }
            if (audienciaforumNew != null && !audienciaforumNew.equals(audienciaforumOld)) {
                audienciaforumNew.getAudienciaCollection().add(audiencia);
                audienciaforumNew = em.merge(audienciaforumNew);
            }
            if (audienciajuizOld != null && !audienciajuizOld.equals(audienciajuizNew)) {
                audienciajuizOld.getAudienciaCollection().remove(audiencia);
                audienciajuizOld = em.merge(audienciajuizOld);
            }
            if (audienciajuizNew != null && !audienciajuizNew.equals(audienciajuizOld)) {
                audienciajuizNew.getAudienciaCollection().add(audiencia);
                audienciajuizNew = em.merge(audienciajuizNew);
            }
            for (Processoaudiencia processoaudienciaCollectionOldProcessoaudiencia : processoaudienciaCollectionOld) {
                if (!processoaudienciaCollectionNew.contains(processoaudienciaCollectionOldProcessoaudiencia)) {
                    processoaudienciaCollectionOldProcessoaudiencia.setProcessoaudienciaaudiencia(null);
                    processoaudienciaCollectionOldProcessoaudiencia = em.merge(processoaudienciaCollectionOldProcessoaudiencia);
                }
            }
            for (Processoaudiencia processoaudienciaCollectionNewProcessoaudiencia : processoaudienciaCollectionNew) {
                if (!processoaudienciaCollectionOld.contains(processoaudienciaCollectionNewProcessoaudiencia)) {
                    Audiencia oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionNewProcessoaudiencia = processoaudienciaCollectionNewProcessoaudiencia.getProcessoaudienciaaudiencia();
                    processoaudienciaCollectionNewProcessoaudiencia.setProcessoaudienciaaudiencia(audiencia);
                    processoaudienciaCollectionNewProcessoaudiencia = em.merge(processoaudienciaCollectionNewProcessoaudiencia);
                    if (oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionNewProcessoaudiencia != null && !oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionNewProcessoaudiencia.equals(audiencia)) {
                        oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionNewProcessoaudiencia.getProcessoaudienciaCollection().remove(processoaudienciaCollectionNewProcessoaudiencia);
                        oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionNewProcessoaudiencia = em.merge(oldProcessoaudienciaaudienciaOfProcessoaudienciaCollectionNewProcessoaudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = audiencia.getId();
                if (findAudiencia(id) == null) {
                    throw new NonexistentEntityException("The audiencia with id " + id + " no longer exists.");
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
            Audiencia audiencia;
            try {
                audiencia = em.getReference(Audiencia.class, id);
                audiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The audiencia with id " + id + " no longer exists.", enfe);
            }
            Forum audienciaforum = audiencia.getAudienciaforum();
            if (audienciaforum != null) {
                audienciaforum.getAudienciaCollection().remove(audiencia);
                audienciaforum = em.merge(audienciaforum);
            }
            Juiz audienciajuiz = audiencia.getAudienciajuiz();
            if (audienciajuiz != null) {
                audienciajuiz.getAudienciaCollection().remove(audiencia);
                audienciajuiz = em.merge(audienciajuiz);
            }
            Collection<Processoaudiencia> processoaudienciaCollection = audiencia.getProcessoaudienciaCollection();
            for (Processoaudiencia processoaudienciaCollectionProcessoaudiencia : processoaudienciaCollection) {
                processoaudienciaCollectionProcessoaudiencia.setProcessoaudienciaaudiencia(null);
                processoaudienciaCollectionProcessoaudiencia = em.merge(processoaudienciaCollectionProcessoaudiencia);
            }
            em.remove(audiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Audiencia> findAudienciaEntities() {
        return findAudienciaEntities(true, -1, -1);
    }

    public List<Audiencia> findAudienciaEntities(int maxResults, int firstResult) {
        return findAudienciaEntities(false, maxResults, firstResult);
    }

    private List<Audiencia> findAudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Audiencia.class));
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

    public Audiencia findAudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Audiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Audiencia> rt = cq.from(Audiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

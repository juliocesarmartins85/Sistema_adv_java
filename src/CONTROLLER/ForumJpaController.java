/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Forum;
import ENTIDADES.Audiencia;
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
public class ForumJpaController implements Serializable {

    public ForumJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Forum forum) {
        if (forum.getAudienciaCollection() == null) {
            forum.setAudienciaCollection(new ArrayList<Audiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Audiencia> attachedAudienciaCollection = new ArrayList<Audiencia>();
            for (Audiencia audienciaCollectionAudienciaToAttach : forum.getAudienciaCollection()) {
                audienciaCollectionAudienciaToAttach = em.getReference(audienciaCollectionAudienciaToAttach.getClass(), audienciaCollectionAudienciaToAttach.getId());
                attachedAudienciaCollection.add(audienciaCollectionAudienciaToAttach);
            }
            forum.setAudienciaCollection(attachedAudienciaCollection);
            em.persist(forum);
            for (Audiencia audienciaCollectionAudiencia : forum.getAudienciaCollection()) {
                Forum oldAudienciaforumOfAudienciaCollectionAudiencia = audienciaCollectionAudiencia.getAudienciaforum();
                audienciaCollectionAudiencia.setAudienciaforum(forum);
                audienciaCollectionAudiencia = em.merge(audienciaCollectionAudiencia);
                if (oldAudienciaforumOfAudienciaCollectionAudiencia != null) {
                    oldAudienciaforumOfAudienciaCollectionAudiencia.getAudienciaCollection().remove(audienciaCollectionAudiencia);
                    oldAudienciaforumOfAudienciaCollectionAudiencia = em.merge(oldAudienciaforumOfAudienciaCollectionAudiencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Forum forum) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Forum persistentForum = em.find(Forum.class, forum.getId());
            Collection<Audiencia> audienciaCollectionOld = persistentForum.getAudienciaCollection();
            Collection<Audiencia> audienciaCollectionNew = forum.getAudienciaCollection();
            List<String> illegalOrphanMessages = null;
            for (Audiencia audienciaCollectionOldAudiencia : audienciaCollectionOld) {
                if (!audienciaCollectionNew.contains(audienciaCollectionOldAudiencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Audiencia " + audienciaCollectionOldAudiencia + " since its audienciaforum field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Audiencia> attachedAudienciaCollectionNew = new ArrayList<Audiencia>();
            for (Audiencia audienciaCollectionNewAudienciaToAttach : audienciaCollectionNew) {
                audienciaCollectionNewAudienciaToAttach = em.getReference(audienciaCollectionNewAudienciaToAttach.getClass(), audienciaCollectionNewAudienciaToAttach.getId());
                attachedAudienciaCollectionNew.add(audienciaCollectionNewAudienciaToAttach);
            }
            audienciaCollectionNew = attachedAudienciaCollectionNew;
            forum.setAudienciaCollection(audienciaCollectionNew);
            forum = em.merge(forum);
            for (Audiencia audienciaCollectionNewAudiencia : audienciaCollectionNew) {
                if (!audienciaCollectionOld.contains(audienciaCollectionNewAudiencia)) {
                    Forum oldAudienciaforumOfAudienciaCollectionNewAudiencia = audienciaCollectionNewAudiencia.getAudienciaforum();
                    audienciaCollectionNewAudiencia.setAudienciaforum(forum);
                    audienciaCollectionNewAudiencia = em.merge(audienciaCollectionNewAudiencia);
                    if (oldAudienciaforumOfAudienciaCollectionNewAudiencia != null && !oldAudienciaforumOfAudienciaCollectionNewAudiencia.equals(forum)) {
                        oldAudienciaforumOfAudienciaCollectionNewAudiencia.getAudienciaCollection().remove(audienciaCollectionNewAudiencia);
                        oldAudienciaforumOfAudienciaCollectionNewAudiencia = em.merge(oldAudienciaforumOfAudienciaCollectionNewAudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = forum.getId();
                if (findForum(id) == null) {
                    throw new NonexistentEntityException("The forum with id " + id + " no longer exists.");
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
            Forum forum;
            try {
                forum = em.getReference(Forum.class, id);
                forum.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The forum with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Audiencia> audienciaCollectionOrphanCheck = forum.getAudienciaCollection();
            for (Audiencia audienciaCollectionOrphanCheckAudiencia : audienciaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Forum (" + forum + ") cannot be destroyed since the Audiencia " + audienciaCollectionOrphanCheckAudiencia + " in its audienciaCollection field has a non-nullable audienciaforum field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(forum);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Forum> findForumEntities() {
        return findForumEntities(true, -1, -1);
    }

    public List<Forum> findForumEntities(int maxResults, int firstResult) {
        return findForumEntities(false, maxResults, firstResult);
    }

    private List<Forum> findForumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Forum.class));
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

    public Forum findForum(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Forum.class, id);
        } finally {
            em.close();
        }
    }

    public int getForumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Forum> rt = cq.from(Forum.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

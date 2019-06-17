/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Juiz;
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
public class JuizJpaController implements Serializable {

    public JuizJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Juiz juiz) {
        if (juiz.getAudienciaCollection() == null) {
            juiz.setAudienciaCollection(new ArrayList<Audiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Audiencia> attachedAudienciaCollection = new ArrayList<Audiencia>();
            for (Audiencia audienciaCollectionAudienciaToAttach : juiz.getAudienciaCollection()) {
                audienciaCollectionAudienciaToAttach = em.getReference(audienciaCollectionAudienciaToAttach.getClass(), audienciaCollectionAudienciaToAttach.getId());
                attachedAudienciaCollection.add(audienciaCollectionAudienciaToAttach);
            }
            juiz.setAudienciaCollection(attachedAudienciaCollection);
            em.persist(juiz);
            for (Audiencia audienciaCollectionAudiencia : juiz.getAudienciaCollection()) {
                Juiz oldAudienciajuizOfAudienciaCollectionAudiencia = audienciaCollectionAudiencia.getAudienciajuiz();
                audienciaCollectionAudiencia.setAudienciajuiz(juiz);
                audienciaCollectionAudiencia = em.merge(audienciaCollectionAudiencia);
                if (oldAudienciajuizOfAudienciaCollectionAudiencia != null) {
                    oldAudienciajuizOfAudienciaCollectionAudiencia.getAudienciaCollection().remove(audienciaCollectionAudiencia);
                    oldAudienciajuizOfAudienciaCollectionAudiencia = em.merge(oldAudienciajuizOfAudienciaCollectionAudiencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Juiz juiz) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Juiz persistentJuiz = em.find(Juiz.class, juiz.getId());
            Collection<Audiencia> audienciaCollectionOld = persistentJuiz.getAudienciaCollection();
            Collection<Audiencia> audienciaCollectionNew = juiz.getAudienciaCollection();
            List<String> illegalOrphanMessages = null;
            for (Audiencia audienciaCollectionOldAudiencia : audienciaCollectionOld) {
                if (!audienciaCollectionNew.contains(audienciaCollectionOldAudiencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Audiencia " + audienciaCollectionOldAudiencia + " since its audienciajuiz field is not nullable.");
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
            juiz.setAudienciaCollection(audienciaCollectionNew);
            juiz = em.merge(juiz);
            for (Audiencia audienciaCollectionNewAudiencia : audienciaCollectionNew) {
                if (!audienciaCollectionOld.contains(audienciaCollectionNewAudiencia)) {
                    Juiz oldAudienciajuizOfAudienciaCollectionNewAudiencia = audienciaCollectionNewAudiencia.getAudienciajuiz();
                    audienciaCollectionNewAudiencia.setAudienciajuiz(juiz);
                    audienciaCollectionNewAudiencia = em.merge(audienciaCollectionNewAudiencia);
                    if (oldAudienciajuizOfAudienciaCollectionNewAudiencia != null && !oldAudienciajuizOfAudienciaCollectionNewAudiencia.equals(juiz)) {
                        oldAudienciajuizOfAudienciaCollectionNewAudiencia.getAudienciaCollection().remove(audienciaCollectionNewAudiencia);
                        oldAudienciajuizOfAudienciaCollectionNewAudiencia = em.merge(oldAudienciajuizOfAudienciaCollectionNewAudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = juiz.getId();
                if (findJuiz(id) == null) {
                    throw new NonexistentEntityException("The juiz with id " + id + " no longer exists.");
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
            Juiz juiz;
            try {
                juiz = em.getReference(Juiz.class, id);
                juiz.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The juiz with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Audiencia> audienciaCollectionOrphanCheck = juiz.getAudienciaCollection();
            for (Audiencia audienciaCollectionOrphanCheckAudiencia : audienciaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Juiz (" + juiz + ") cannot be destroyed since the Audiencia " + audienciaCollectionOrphanCheckAudiencia + " in its audienciaCollection field has a non-nullable audienciajuiz field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(juiz);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Juiz> findJuizEntities() {
        return findJuizEntities(true, -1, -1);
    }

    public List<Juiz> findJuizEntities(int maxResults, int firstResult) {
        return findJuizEntities(false, maxResults, firstResult);
    }

    private List<Juiz> findJuizEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Juiz.class));
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

    public Juiz findJuiz(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Juiz.class, id);
        } finally {
            em.close();
        }
    }

    public int getJuizCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Juiz> rt = cq.from(Juiz.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

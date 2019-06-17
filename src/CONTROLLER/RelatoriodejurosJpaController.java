/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Relatoriodejuros;
import ENTIDADES.Peticaotabeladejuros;
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
public class RelatoriodejurosJpaController implements Serializable {

    public RelatoriodejurosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Relatoriodejuros relatoriodejuros) {
        if (relatoriodejuros.getPeticaotabeladejurosCollection() == null) {
            relatoriodejuros.setPeticaotabeladejurosCollection(new ArrayList<Peticaotabeladejuros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Peticaotabeladejuros> attachedPeticaotabeladejurosCollection = new ArrayList<Peticaotabeladejuros>();
            for (Peticaotabeladejuros peticaotabeladejurosCollectionPeticaotabeladejurosToAttach : relatoriodejuros.getPeticaotabeladejurosCollection()) {
                peticaotabeladejurosCollectionPeticaotabeladejurosToAttach = em.getReference(peticaotabeladejurosCollectionPeticaotabeladejurosToAttach.getClass(), peticaotabeladejurosCollectionPeticaotabeladejurosToAttach.getId());
                attachedPeticaotabeladejurosCollection.add(peticaotabeladejurosCollectionPeticaotabeladejurosToAttach);
            }
            relatoriodejuros.setPeticaotabeladejurosCollection(attachedPeticaotabeladejurosCollection);
            em.persist(relatoriodejuros);
            for (Peticaotabeladejuros peticaotabeladejurosCollectionPeticaotabeladejuros : relatoriodejuros.getPeticaotabeladejurosCollection()) {
                Relatoriodejuros oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionPeticaotabeladejuros = peticaotabeladejurosCollectionPeticaotabeladejuros.getPeticaotabeladejurostabeladejuros();
                peticaotabeladejurosCollectionPeticaotabeladejuros.setPeticaotabeladejurostabeladejuros(relatoriodejuros);
                peticaotabeladejurosCollectionPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionPeticaotabeladejuros);
                if (oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionPeticaotabeladejuros != null) {
                    oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionPeticaotabeladejuros.getPeticaotabeladejurosCollection().remove(peticaotabeladejurosCollectionPeticaotabeladejuros);
                    oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionPeticaotabeladejuros = em.merge(oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionPeticaotabeladejuros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Relatoriodejuros relatoriodejuros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Relatoriodejuros persistentRelatoriodejuros = em.find(Relatoriodejuros.class, relatoriodejuros.getId());
            Collection<Peticaotabeladejuros> peticaotabeladejurosCollectionOld = persistentRelatoriodejuros.getPeticaotabeladejurosCollection();
            Collection<Peticaotabeladejuros> peticaotabeladejurosCollectionNew = relatoriodejuros.getPeticaotabeladejurosCollection();
            Collection<Peticaotabeladejuros> attachedPeticaotabeladejurosCollectionNew = new ArrayList<Peticaotabeladejuros>();
            for (Peticaotabeladejuros peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach : peticaotabeladejurosCollectionNew) {
                peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach = em.getReference(peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach.getClass(), peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach.getId());
                attachedPeticaotabeladejurosCollectionNew.add(peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach);
            }
            peticaotabeladejurosCollectionNew = attachedPeticaotabeladejurosCollectionNew;
            relatoriodejuros.setPeticaotabeladejurosCollection(peticaotabeladejurosCollectionNew);
            relatoriodejuros = em.merge(relatoriodejuros);
            for (Peticaotabeladejuros peticaotabeladejurosCollectionOldPeticaotabeladejuros : peticaotabeladejurosCollectionOld) {
                if (!peticaotabeladejurosCollectionNew.contains(peticaotabeladejurosCollectionOldPeticaotabeladejuros)) {
                    peticaotabeladejurosCollectionOldPeticaotabeladejuros.setPeticaotabeladejurostabeladejuros(null);
                    peticaotabeladejurosCollectionOldPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionOldPeticaotabeladejuros);
                }
            }
            for (Peticaotabeladejuros peticaotabeladejurosCollectionNewPeticaotabeladejuros : peticaotabeladejurosCollectionNew) {
                if (!peticaotabeladejurosCollectionOld.contains(peticaotabeladejurosCollectionNewPeticaotabeladejuros)) {
                    Relatoriodejuros oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros = peticaotabeladejurosCollectionNewPeticaotabeladejuros.getPeticaotabeladejurostabeladejuros();
                    peticaotabeladejurosCollectionNewPeticaotabeladejuros.setPeticaotabeladejurostabeladejuros(relatoriodejuros);
                    peticaotabeladejurosCollectionNewPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionNewPeticaotabeladejuros);
                    if (oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros != null && !oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros.equals(relatoriodejuros)) {
                        oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros.getPeticaotabeladejurosCollection().remove(peticaotabeladejurosCollectionNewPeticaotabeladejuros);
                        oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros = em.merge(oldPeticaotabeladejurostabeladejurosOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = relatoriodejuros.getId();
                if (findRelatoriodejuros(id) == null) {
                    throw new NonexistentEntityException("The relatoriodejuros with id " + id + " no longer exists.");
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
            Relatoriodejuros relatoriodejuros;
            try {
                relatoriodejuros = em.getReference(Relatoriodejuros.class, id);
                relatoriodejuros.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The relatoriodejuros with id " + id + " no longer exists.", enfe);
            }
            Collection<Peticaotabeladejuros> peticaotabeladejurosCollection = relatoriodejuros.getPeticaotabeladejurosCollection();
            for (Peticaotabeladejuros peticaotabeladejurosCollectionPeticaotabeladejuros : peticaotabeladejurosCollection) {
                peticaotabeladejurosCollectionPeticaotabeladejuros.setPeticaotabeladejurostabeladejuros(null);
                peticaotabeladejurosCollectionPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionPeticaotabeladejuros);
            }
            em.remove(relatoriodejuros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Relatoriodejuros> findRelatoriodejurosEntities() {
        return findRelatoriodejurosEntities(true, -1, -1);
    }

    public List<Relatoriodejuros> findRelatoriodejurosEntities(int maxResults, int firstResult) {
        return findRelatoriodejurosEntities(false, maxResults, firstResult);
    }

    private List<Relatoriodejuros> findRelatoriodejurosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Relatoriodejuros.class));
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

    public Relatoriodejuros findRelatoriodejuros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Relatoriodejuros.class, id);
        } finally {
            em.close();
        }
    }

    public int getRelatoriodejurosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Relatoriodejuros> rt = cq.from(Relatoriodejuros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Estado;
import ENTIDADES.Cidade;
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
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) {
        if (estado.getCidadeCollection() == null) {
            estado.setCidadeCollection(new ArrayList<Cidade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cidade> attachedCidadeCollection = new ArrayList<Cidade>();
            for (Cidade cidadeCollectionCidadeToAttach : estado.getCidadeCollection()) {
                cidadeCollectionCidadeToAttach = em.getReference(cidadeCollectionCidadeToAttach.getClass(), cidadeCollectionCidadeToAttach.getId());
                attachedCidadeCollection.add(cidadeCollectionCidadeToAttach);
            }
            estado.setCidadeCollection(attachedCidadeCollection);
            em.persist(estado);
            for (Cidade cidadeCollectionCidade : estado.getCidadeCollection()) {
                Estado oldCidadeestadoOfCidadeCollectionCidade = cidadeCollectionCidade.getCidadeestado();
                cidadeCollectionCidade.setCidadeestado(estado);
                cidadeCollectionCidade = em.merge(cidadeCollectionCidade);
                if (oldCidadeestadoOfCidadeCollectionCidade != null) {
                    oldCidadeestadoOfCidadeCollectionCidade.getCidadeCollection().remove(cidadeCollectionCidade);
                    oldCidadeestadoOfCidadeCollectionCidade = em.merge(oldCidadeestadoOfCidadeCollectionCidade);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getId());
            Collection<Cidade> cidadeCollectionOld = persistentEstado.getCidadeCollection();
            Collection<Cidade> cidadeCollectionNew = estado.getCidadeCollection();
            List<String> illegalOrphanMessages = null;
            for (Cidade cidadeCollectionOldCidade : cidadeCollectionOld) {
                if (!cidadeCollectionNew.contains(cidadeCollectionOldCidade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cidade " + cidadeCollectionOldCidade + " since its cidadeestado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cidade> attachedCidadeCollectionNew = new ArrayList<Cidade>();
            for (Cidade cidadeCollectionNewCidadeToAttach : cidadeCollectionNew) {
                cidadeCollectionNewCidadeToAttach = em.getReference(cidadeCollectionNewCidadeToAttach.getClass(), cidadeCollectionNewCidadeToAttach.getId());
                attachedCidadeCollectionNew.add(cidadeCollectionNewCidadeToAttach);
            }
            cidadeCollectionNew = attachedCidadeCollectionNew;
            estado.setCidadeCollection(cidadeCollectionNew);
            estado = em.merge(estado);
            for (Cidade cidadeCollectionNewCidade : cidadeCollectionNew) {
                if (!cidadeCollectionOld.contains(cidadeCollectionNewCidade)) {
                    Estado oldCidadeestadoOfCidadeCollectionNewCidade = cidadeCollectionNewCidade.getCidadeestado();
                    cidadeCollectionNewCidade.setCidadeestado(estado);
                    cidadeCollectionNewCidade = em.merge(cidadeCollectionNewCidade);
                    if (oldCidadeestadoOfCidadeCollectionNewCidade != null && !oldCidadeestadoOfCidadeCollectionNewCidade.equals(estado)) {
                        oldCidadeestadoOfCidadeCollectionNewCidade.getCidadeCollection().remove(cidadeCollectionNewCidade);
                        oldCidadeestadoOfCidadeCollectionNewCidade = em.merge(oldCidadeestadoOfCidadeCollectionNewCidade);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getId();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cidade> cidadeCollectionOrphanCheck = estado.getCidadeCollection();
            for (Cidade cidadeCollectionOrphanCheckCidade : cidadeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Cidade " + cidadeCollectionOrphanCheckCidade + " in its cidadeCollection field has a non-nullable cidadeestado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

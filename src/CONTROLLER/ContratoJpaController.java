/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Contrato;
import ENTIDADES.Pessoa;
import ENTIDADES.Contratopessoa;
import CONTROLLER.exceptions.IllegalOrphanException;
import CONTROLLER.exceptions.NonexistentEntityException;
import CONTROLLER.exceptions.PreexistingEntityException;
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
public class ContratoJpaController implements Serializable {

    public ContratoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contrato contrato) throws PreexistingEntityException, Exception {
        if (contrato.getContratopessoaCollection() == null) {
            contrato.setContratopessoaCollection(new ArrayList<Contratopessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa contratopessoa = contrato.getContratopessoa();
            if (contratopessoa != null) {
                contratopessoa = em.getReference(contratopessoa.getClass(), contratopessoa.getId());
                contrato.setContratopessoa(contratopessoa);
            }
            Collection<Contratopessoa> attachedContratopessoaCollection = new ArrayList<Contratopessoa>();
            for (Contratopessoa contratopessoaCollectionContratopessoaToAttach : contrato.getContratopessoaCollection()) {
                contratopessoaCollectionContratopessoaToAttach = em.getReference(contratopessoaCollectionContratopessoaToAttach.getClass(), contratopessoaCollectionContratopessoaToAttach.getId());
                attachedContratopessoaCollection.add(contratopessoaCollectionContratopessoaToAttach);
            }
            contrato.setContratopessoaCollection(attachedContratopessoaCollection);
            em.persist(contrato);
            if (contratopessoa != null) {
                contratopessoa.getContratoCollection().add(contrato);
                contratopessoa = em.merge(contratopessoa);
            }
            for (Contratopessoa contratopessoaCollectionContratopessoa : contrato.getContratopessoaCollection()) {
                Contrato oldContratopessoacontratoOfContratopessoaCollectionContratopessoa = contratopessoaCollectionContratopessoa.getContratopessoacontrato();
                contratopessoaCollectionContratopessoa.setContratopessoacontrato(contrato);
                contratopessoaCollectionContratopessoa = em.merge(contratopessoaCollectionContratopessoa);
                if (oldContratopessoacontratoOfContratopessoaCollectionContratopessoa != null) {
                    oldContratopessoacontratoOfContratopessoaCollectionContratopessoa.getContratopessoaCollection().remove(contratopessoaCollectionContratopessoa);
                    oldContratopessoacontratoOfContratopessoaCollectionContratopessoa = em.merge(oldContratopessoacontratoOfContratopessoaCollectionContratopessoa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContrato(contrato.getId()) != null) {
                throw new PreexistingEntityException("Contrato " + contrato + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contrato contrato) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contrato persistentContrato = em.find(Contrato.class, contrato.getId());
            Pessoa contratopessoaOld = persistentContrato.getContratopessoa();
            Pessoa contratopessoaNew = contrato.getContratopessoa();
            Collection<Contratopessoa> contratopessoaCollectionOld = persistentContrato.getContratopessoaCollection();
            Collection<Contratopessoa> contratopessoaCollectionNew = contrato.getContratopessoaCollection();
            List<String> illegalOrphanMessages = null;
            for (Contratopessoa contratopessoaCollectionOldContratopessoa : contratopessoaCollectionOld) {
                if (!contratopessoaCollectionNew.contains(contratopessoaCollectionOldContratopessoa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contratopessoa " + contratopessoaCollectionOldContratopessoa + " since its contratopessoacontrato field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (contratopessoaNew != null) {
                contratopessoaNew = em.getReference(contratopessoaNew.getClass(), contratopessoaNew.getId());
                contrato.setContratopessoa(contratopessoaNew);
            }
            Collection<Contratopessoa> attachedContratopessoaCollectionNew = new ArrayList<Contratopessoa>();
            for (Contratopessoa contratopessoaCollectionNewContratopessoaToAttach : contratopessoaCollectionNew) {
                contratopessoaCollectionNewContratopessoaToAttach = em.getReference(contratopessoaCollectionNewContratopessoaToAttach.getClass(), contratopessoaCollectionNewContratopessoaToAttach.getId());
                attachedContratopessoaCollectionNew.add(contratopessoaCollectionNewContratopessoaToAttach);
            }
            contratopessoaCollectionNew = attachedContratopessoaCollectionNew;
            contrato.setContratopessoaCollection(contratopessoaCollectionNew);
            contrato = em.merge(contrato);
            if (contratopessoaOld != null && !contratopessoaOld.equals(contratopessoaNew)) {
                contratopessoaOld.getContratoCollection().remove(contrato);
                contratopessoaOld = em.merge(contratopessoaOld);
            }
            if (contratopessoaNew != null && !contratopessoaNew.equals(contratopessoaOld)) {
                contratopessoaNew.getContratoCollection().add(contrato);
                contratopessoaNew = em.merge(contratopessoaNew);
            }
            for (Contratopessoa contratopessoaCollectionNewContratopessoa : contratopessoaCollectionNew) {
                if (!contratopessoaCollectionOld.contains(contratopessoaCollectionNewContratopessoa)) {
                    Contrato oldContratopessoacontratoOfContratopessoaCollectionNewContratopessoa = contratopessoaCollectionNewContratopessoa.getContratopessoacontrato();
                    contratopessoaCollectionNewContratopessoa.setContratopessoacontrato(contrato);
                    contratopessoaCollectionNewContratopessoa = em.merge(contratopessoaCollectionNewContratopessoa);
                    if (oldContratopessoacontratoOfContratopessoaCollectionNewContratopessoa != null && !oldContratopessoacontratoOfContratopessoaCollectionNewContratopessoa.equals(contrato)) {
                        oldContratopessoacontratoOfContratopessoaCollectionNewContratopessoa.getContratopessoaCollection().remove(contratopessoaCollectionNewContratopessoa);
                        oldContratopessoacontratoOfContratopessoaCollectionNewContratopessoa = em.merge(oldContratopessoacontratoOfContratopessoaCollectionNewContratopessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contrato.getId();
                if (findContrato(id) == null) {
                    throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.");
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
            Contrato contrato;
            try {
                contrato = em.getReference(Contrato.class, id);
                contrato.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contrato with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Contratopessoa> contratopessoaCollectionOrphanCheck = contrato.getContratopessoaCollection();
            for (Contratopessoa contratopessoaCollectionOrphanCheckContratopessoa : contratopessoaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contrato (" + contrato + ") cannot be destroyed since the Contratopessoa " + contratopessoaCollectionOrphanCheckContratopessoa + " in its contratopessoaCollection field has a non-nullable contratopessoacontrato field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pessoa contratopessoa = contrato.getContratopessoa();
            if (contratopessoa != null) {
                contratopessoa.getContratoCollection().remove(contrato);
                contratopessoa = em.merge(contratopessoa);
            }
            em.remove(contrato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contrato> findContratoEntities() {
        return findContratoEntities(true, -1, -1);
    }

    public List<Contrato> findContratoEntities(int maxResults, int firstResult) {
        return findContratoEntities(false, maxResults, firstResult);
    }

    private List<Contrato> findContratoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contrato.class));
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

    public Contrato findContrato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contrato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contrato> rt = cq.from(Contrato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

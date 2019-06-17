/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Bairro;
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
public class CidadeJpaController implements Serializable {

    public CidadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cidade cidade) {
        if (cidade.getBairroCollection() == null) {
            cidade.setBairroCollection(new ArrayList<Bairro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado cidadeestado = cidade.getCidadeestado();
            if (cidadeestado != null) {
                cidadeestado = em.getReference(cidadeestado.getClass(), cidadeestado.getId());
                cidade.setCidadeestado(cidadeestado);
            }
            Collection<Bairro> attachedBairroCollection = new ArrayList<Bairro>();
            for (Bairro bairroCollectionBairroToAttach : cidade.getBairroCollection()) {
                bairroCollectionBairroToAttach = em.getReference(bairroCollectionBairroToAttach.getClass(), bairroCollectionBairroToAttach.getId());
                attachedBairroCollection.add(bairroCollectionBairroToAttach);
            }
            cidade.setBairroCollection(attachedBairroCollection);
            em.persist(cidade);
            if (cidadeestado != null) {
                cidadeestado.getCidadeCollection().add(cidade);
                cidadeestado = em.merge(cidadeestado);
            }
            for (Bairro bairroCollectionBairro : cidade.getBairroCollection()) {
                Cidade oldBairrocidadeOfBairroCollectionBairro = bairroCollectionBairro.getBairrocidade();
                bairroCollectionBairro.setBairrocidade(cidade);
                bairroCollectionBairro = em.merge(bairroCollectionBairro);
                if (oldBairrocidadeOfBairroCollectionBairro != null) {
                    oldBairrocidadeOfBairroCollectionBairro.getBairroCollection().remove(bairroCollectionBairro);
                    oldBairrocidadeOfBairroCollectionBairro = em.merge(oldBairrocidadeOfBairroCollectionBairro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cidade cidade) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade persistentCidade = em.find(Cidade.class, cidade.getId());
            Estado cidadeestadoOld = persistentCidade.getCidadeestado();
            Estado cidadeestadoNew = cidade.getCidadeestado();
            Collection<Bairro> bairroCollectionOld = persistentCidade.getBairroCollection();
            Collection<Bairro> bairroCollectionNew = cidade.getBairroCollection();
            List<String> illegalOrphanMessages = null;
            for (Bairro bairroCollectionOldBairro : bairroCollectionOld) {
                if (!bairroCollectionNew.contains(bairroCollectionOldBairro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bairro " + bairroCollectionOldBairro + " since its bairrocidade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cidadeestadoNew != null) {
                cidadeestadoNew = em.getReference(cidadeestadoNew.getClass(), cidadeestadoNew.getId());
                cidade.setCidadeestado(cidadeestadoNew);
            }
            Collection<Bairro> attachedBairroCollectionNew = new ArrayList<Bairro>();
            for (Bairro bairroCollectionNewBairroToAttach : bairroCollectionNew) {
                bairroCollectionNewBairroToAttach = em.getReference(bairroCollectionNewBairroToAttach.getClass(), bairroCollectionNewBairroToAttach.getId());
                attachedBairroCollectionNew.add(bairroCollectionNewBairroToAttach);
            }
            bairroCollectionNew = attachedBairroCollectionNew;
            cidade.setBairroCollection(bairroCollectionNew);
            cidade = em.merge(cidade);
            if (cidadeestadoOld != null && !cidadeestadoOld.equals(cidadeestadoNew)) {
                cidadeestadoOld.getCidadeCollection().remove(cidade);
                cidadeestadoOld = em.merge(cidadeestadoOld);
            }
            if (cidadeestadoNew != null && !cidadeestadoNew.equals(cidadeestadoOld)) {
                cidadeestadoNew.getCidadeCollection().add(cidade);
                cidadeestadoNew = em.merge(cidadeestadoNew);
            }
            for (Bairro bairroCollectionNewBairro : bairroCollectionNew) {
                if (!bairroCollectionOld.contains(bairroCollectionNewBairro)) {
                    Cidade oldBairrocidadeOfBairroCollectionNewBairro = bairroCollectionNewBairro.getBairrocidade();
                    bairroCollectionNewBairro.setBairrocidade(cidade);
                    bairroCollectionNewBairro = em.merge(bairroCollectionNewBairro);
                    if (oldBairrocidadeOfBairroCollectionNewBairro != null && !oldBairrocidadeOfBairroCollectionNewBairro.equals(cidade)) {
                        oldBairrocidadeOfBairroCollectionNewBairro.getBairroCollection().remove(bairroCollectionNewBairro);
                        oldBairrocidadeOfBairroCollectionNewBairro = em.merge(oldBairrocidadeOfBairroCollectionNewBairro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cidade.getId();
                if (findCidade(id) == null) {
                    throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.");
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
            Cidade cidade;
            try {
                cidade = em.getReference(Cidade.class, id);
                cidade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Bairro> bairroCollectionOrphanCheck = cidade.getBairroCollection();
            for (Bairro bairroCollectionOrphanCheckBairro : bairroCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cidade (" + cidade + ") cannot be destroyed since the Bairro " + bairroCollectionOrphanCheckBairro + " in its bairroCollection field has a non-nullable bairrocidade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estado cidadeestado = cidade.getCidadeestado();
            if (cidadeestado != null) {
                cidadeestado.getCidadeCollection().remove(cidade);
                cidadeestado = em.merge(cidadeestado);
            }
            em.remove(cidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cidade> findCidadeEntities() {
        return findCidadeEntities(true, -1, -1);
    }

    public List<Cidade> findCidadeEntities(int maxResults, int firstResult) {
        return findCidadeEntities(false, maxResults, firstResult);
    }

    private List<Cidade> findCidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cidade.class));
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

    public Cidade findCidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getCidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cidade> rt = cq.from(Cidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

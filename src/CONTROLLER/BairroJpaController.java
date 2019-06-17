/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Bairro;
import ENTIDADES.Rua;
import ENTIDADES.Cidade;
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
public class BairroJpaController implements Serializable {

    public BairroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bairro bairro) {
        if (bairro.getRuaCollection() == null) {
            bairro.setRuaCollection(new ArrayList<Rua>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade bairrocidade = bairro.getBairrocidade();
            if (bairrocidade != null) {
                bairrocidade = em.getReference(bairrocidade.getClass(), bairrocidade.getId());
                bairro.setBairrocidade(bairrocidade);
            }
            Collection<Rua> attachedRuaCollection = new ArrayList<Rua>();
            for (Rua ruaCollectionRuaToAttach : bairro.getRuaCollection()) {
                ruaCollectionRuaToAttach = em.getReference(ruaCollectionRuaToAttach.getClass(), ruaCollectionRuaToAttach.getId());
                attachedRuaCollection.add(ruaCollectionRuaToAttach);
            }
            bairro.setRuaCollection(attachedRuaCollection);
            em.persist(bairro);
            if (bairrocidade != null) {
                bairrocidade.getBairroCollection().add(bairro);
                bairrocidade = em.merge(bairrocidade);
            }
            for (Rua ruaCollectionRua : bairro.getRuaCollection()) {
                Bairro oldRuabairroOfRuaCollectionRua = ruaCollectionRua.getRuabairro();
                ruaCollectionRua.setRuabairro(bairro);
                ruaCollectionRua = em.merge(ruaCollectionRua);
                if (oldRuabairroOfRuaCollectionRua != null) {
                    oldRuabairroOfRuaCollectionRua.getRuaCollection().remove(ruaCollectionRua);
                    oldRuabairroOfRuaCollectionRua = em.merge(oldRuabairroOfRuaCollectionRua);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bairro bairro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro persistentBairro = em.find(Bairro.class, bairro.getId());
            Cidade bairrocidadeOld = persistentBairro.getBairrocidade();
            Cidade bairrocidadeNew = bairro.getBairrocidade();
            Collection<Rua> ruaCollectionOld = persistentBairro.getRuaCollection();
            Collection<Rua> ruaCollectionNew = bairro.getRuaCollection();
            if (bairrocidadeNew != null) {
                bairrocidadeNew = em.getReference(bairrocidadeNew.getClass(), bairrocidadeNew.getId());
                bairro.setBairrocidade(bairrocidadeNew);
            }
            Collection<Rua> attachedRuaCollectionNew = new ArrayList<Rua>();
            for (Rua ruaCollectionNewRuaToAttach : ruaCollectionNew) {
                ruaCollectionNewRuaToAttach = em.getReference(ruaCollectionNewRuaToAttach.getClass(), ruaCollectionNewRuaToAttach.getId());
                attachedRuaCollectionNew.add(ruaCollectionNewRuaToAttach);
            }
            ruaCollectionNew = attachedRuaCollectionNew;
            bairro.setRuaCollection(ruaCollectionNew);
            bairro = em.merge(bairro);
            if (bairrocidadeOld != null && !bairrocidadeOld.equals(bairrocidadeNew)) {
                bairrocidadeOld.getBairroCollection().remove(bairro);
                bairrocidadeOld = em.merge(bairrocidadeOld);
            }
            if (bairrocidadeNew != null && !bairrocidadeNew.equals(bairrocidadeOld)) {
                bairrocidadeNew.getBairroCollection().add(bairro);
                bairrocidadeNew = em.merge(bairrocidadeNew);
            }
            for (Rua ruaCollectionOldRua : ruaCollectionOld) {
                if (!ruaCollectionNew.contains(ruaCollectionOldRua)) {
                    ruaCollectionOldRua.setRuabairro(null);
                    ruaCollectionOldRua = em.merge(ruaCollectionOldRua);
                }
            }
            for (Rua ruaCollectionNewRua : ruaCollectionNew) {
                if (!ruaCollectionOld.contains(ruaCollectionNewRua)) {
                    Bairro oldRuabairroOfRuaCollectionNewRua = ruaCollectionNewRua.getRuabairro();
                    ruaCollectionNewRua.setRuabairro(bairro);
                    ruaCollectionNewRua = em.merge(ruaCollectionNewRua);
                    if (oldRuabairroOfRuaCollectionNewRua != null && !oldRuabairroOfRuaCollectionNewRua.equals(bairro)) {
                        oldRuabairroOfRuaCollectionNewRua.getRuaCollection().remove(ruaCollectionNewRua);
                        oldRuabairroOfRuaCollectionNewRua = em.merge(oldRuabairroOfRuaCollectionNewRua);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bairro.getId();
                if (findBairro(id) == null) {
                    throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.");
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
            Bairro bairro;
            try {
                bairro = em.getReference(Bairro.class, id);
                bairro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.", enfe);
            }
            Cidade bairrocidade = bairro.getBairrocidade();
            if (bairrocidade != null) {
                bairrocidade.getBairroCollection().remove(bairro);
                bairrocidade = em.merge(bairrocidade);
            }
            Collection<Rua> ruaCollection = bairro.getRuaCollection();
            for (Rua ruaCollectionRua : ruaCollection) {
                ruaCollectionRua.setRuabairro(null);
                ruaCollectionRua = em.merge(ruaCollectionRua);
            }
            em.remove(bairro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bairro> findBairroEntities() {
        return findBairroEntities(true, -1, -1);
    }

    public List<Bairro> findBairroEntities(int maxResults, int firstResult) {
        return findBairroEntities(false, maxResults, firstResult);
    }

    private List<Bairro> findBairroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bairro.class));
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

    public Bairro findBairro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bairro.class, id);
        } finally {
            em.close();
        }
    }

    public int getBairroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bairro> rt = cq.from(Bairro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

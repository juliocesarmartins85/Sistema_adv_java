/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Bairro;
import ENTIDADES.Pessoa;
import ENTIDADES.Rua;
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
public class RuaJpaController implements Serializable {

    public RuaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rua rua) {
        if (rua.getPessoaCollection() == null) {
            rua.setPessoaCollection(new ArrayList<Pessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro ruabairro = rua.getRuabairro();
            if (ruabairro != null) {
                ruabairro = em.getReference(ruabairro.getClass(), ruabairro.getId());
                rua.setRuabairro(ruabairro);
            }
            Collection<Pessoa> attachedPessoaCollection = new ArrayList<Pessoa>();
            for (Pessoa pessoaCollectionPessoaToAttach : rua.getPessoaCollection()) {
                pessoaCollectionPessoaToAttach = em.getReference(pessoaCollectionPessoaToAttach.getClass(), pessoaCollectionPessoaToAttach.getId());
                attachedPessoaCollection.add(pessoaCollectionPessoaToAttach);
            }
            rua.setPessoaCollection(attachedPessoaCollection);
            em.persist(rua);
            if (ruabairro != null) {
                ruabairro.getRuaCollection().add(rua);
                ruabairro = em.merge(ruabairro);
            }
            for (Pessoa pessoaCollectionPessoa : rua.getPessoaCollection()) {
                Rua oldPessoaruaOfPessoaCollectionPessoa = pessoaCollectionPessoa.getPessoarua();
                pessoaCollectionPessoa.setPessoarua(rua);
                pessoaCollectionPessoa = em.merge(pessoaCollectionPessoa);
                if (oldPessoaruaOfPessoaCollectionPessoa != null) {
                    oldPessoaruaOfPessoaCollectionPessoa.getPessoaCollection().remove(pessoaCollectionPessoa);
                    oldPessoaruaOfPessoaCollectionPessoa = em.merge(oldPessoaruaOfPessoaCollectionPessoa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rua rua) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rua persistentRua = em.find(Rua.class, rua.getId());
            Bairro ruabairroOld = persistentRua.getRuabairro();
            Bairro ruabairroNew = rua.getRuabairro();
            Collection<Pessoa> pessoaCollectionOld = persistentRua.getPessoaCollection();
            Collection<Pessoa> pessoaCollectionNew = rua.getPessoaCollection();
            if (ruabairroNew != null) {
                ruabairroNew = em.getReference(ruabairroNew.getClass(), ruabairroNew.getId());
                rua.setRuabairro(ruabairroNew);
            }
            Collection<Pessoa> attachedPessoaCollectionNew = new ArrayList<Pessoa>();
            for (Pessoa pessoaCollectionNewPessoaToAttach : pessoaCollectionNew) {
                pessoaCollectionNewPessoaToAttach = em.getReference(pessoaCollectionNewPessoaToAttach.getClass(), pessoaCollectionNewPessoaToAttach.getId());
                attachedPessoaCollectionNew.add(pessoaCollectionNewPessoaToAttach);
            }
            pessoaCollectionNew = attachedPessoaCollectionNew;
            rua.setPessoaCollection(pessoaCollectionNew);
            rua = em.merge(rua);
            if (ruabairroOld != null && !ruabairroOld.equals(ruabairroNew)) {
                ruabairroOld.getRuaCollection().remove(rua);
                ruabairroOld = em.merge(ruabairroOld);
            }
            if (ruabairroNew != null && !ruabairroNew.equals(ruabairroOld)) {
                ruabairroNew.getRuaCollection().add(rua);
                ruabairroNew = em.merge(ruabairroNew);
            }
            for (Pessoa pessoaCollectionOldPessoa : pessoaCollectionOld) {
                if (!pessoaCollectionNew.contains(pessoaCollectionOldPessoa)) {
                    pessoaCollectionOldPessoa.setPessoarua(null);
                    pessoaCollectionOldPessoa = em.merge(pessoaCollectionOldPessoa);
                }
            }
            for (Pessoa pessoaCollectionNewPessoa : pessoaCollectionNew) {
                if (!pessoaCollectionOld.contains(pessoaCollectionNewPessoa)) {
                    Rua oldPessoaruaOfPessoaCollectionNewPessoa = pessoaCollectionNewPessoa.getPessoarua();
                    pessoaCollectionNewPessoa.setPessoarua(rua);
                    pessoaCollectionNewPessoa = em.merge(pessoaCollectionNewPessoa);
                    if (oldPessoaruaOfPessoaCollectionNewPessoa != null && !oldPessoaruaOfPessoaCollectionNewPessoa.equals(rua)) {
                        oldPessoaruaOfPessoaCollectionNewPessoa.getPessoaCollection().remove(pessoaCollectionNewPessoa);
                        oldPessoaruaOfPessoaCollectionNewPessoa = em.merge(oldPessoaruaOfPessoaCollectionNewPessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rua.getId();
                if (findRua(id) == null) {
                    throw new NonexistentEntityException("The rua with id " + id + " no longer exists.");
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
            Rua rua;
            try {
                rua = em.getReference(Rua.class, id);
                rua.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rua with id " + id + " no longer exists.", enfe);
            }
            Bairro ruabairro = rua.getRuabairro();
            if (ruabairro != null) {
                ruabairro.getRuaCollection().remove(rua);
                ruabairro = em.merge(ruabairro);
            }
            Collection<Pessoa> pessoaCollection = rua.getPessoaCollection();
            for (Pessoa pessoaCollectionPessoa : pessoaCollection) {
                pessoaCollectionPessoa.setPessoarua(null);
                pessoaCollectionPessoa = em.merge(pessoaCollectionPessoa);
            }
            em.remove(rua);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rua> findRuaEntities() {
        return findRuaEntities(true, -1, -1);
    }

    public List<Rua> findRuaEntities(int maxResults, int firstResult) {
        return findRuaEntities(false, maxResults, firstResult);
    }

    private List<Rua> findRuaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rua.class));
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

    public Rua findRua(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rua.class, id);
        } finally {
            em.close();
        }
    }

    public int getRuaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rua> rt = cq.from(Rua.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

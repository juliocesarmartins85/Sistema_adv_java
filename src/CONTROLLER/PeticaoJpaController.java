/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Peticaopessoa;
import ENTIDADES.Processopeticao;
import ENTIDADES.Peticaotabeladejuros;
import ENTIDADES.Peticao;
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
public class PeticaoJpaController implements Serializable {

    public PeticaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Peticao peticao) {
        if (peticao.getPeticaotabeladejurosCollection() == null) {
            peticao.setPeticaotabeladejurosCollection(new ArrayList<Peticaotabeladejuros>());
        }
        if (peticao.getProcessopeticaoCollection() == null) {
            peticao.setProcessopeticaoCollection(new ArrayList<Processopeticao>());
        }
        if (peticao.getPeticaopessoaCollection() == null) {
            peticao.setPeticaopessoaCollection(new ArrayList<Peticaopessoa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Peticaotabeladejuros> attachedPeticaotabeladejurosCollection = new ArrayList<Peticaotabeladejuros>();
            for (Peticaotabeladejuros peticaotabeladejurosCollectionPeticaotabeladejurosToAttach : peticao.getPeticaotabeladejurosCollection()) {
                peticaotabeladejurosCollectionPeticaotabeladejurosToAttach = em.getReference(peticaotabeladejurosCollectionPeticaotabeladejurosToAttach.getClass(), peticaotabeladejurosCollectionPeticaotabeladejurosToAttach.getId());
                attachedPeticaotabeladejurosCollection.add(peticaotabeladejurosCollectionPeticaotabeladejurosToAttach);
            }
            peticao.setPeticaotabeladejurosCollection(attachedPeticaotabeladejurosCollection);
            Collection<Processopeticao> attachedProcessopeticaoCollection = new ArrayList<Processopeticao>();
            for (Processopeticao processopeticaoCollectionProcessopeticaoToAttach : peticao.getProcessopeticaoCollection()) {
                processopeticaoCollectionProcessopeticaoToAttach = em.getReference(processopeticaoCollectionProcessopeticaoToAttach.getClass(), processopeticaoCollectionProcessopeticaoToAttach.getId());
                attachedProcessopeticaoCollection.add(processopeticaoCollectionProcessopeticaoToAttach);
            }
            peticao.setProcessopeticaoCollection(attachedProcessopeticaoCollection);
            Collection<Peticaopessoa> attachedPeticaopessoaCollection = new ArrayList<Peticaopessoa>();
            for (Peticaopessoa peticaopessoaCollectionPeticaopessoaToAttach : peticao.getPeticaopessoaCollection()) {
                peticaopessoaCollectionPeticaopessoaToAttach = em.getReference(peticaopessoaCollectionPeticaopessoaToAttach.getClass(), peticaopessoaCollectionPeticaopessoaToAttach.getId());
                attachedPeticaopessoaCollection.add(peticaopessoaCollectionPeticaopessoaToAttach);
            }
            peticao.setPeticaopessoaCollection(attachedPeticaopessoaCollection);
            em.persist(peticao);
            for (Peticaotabeladejuros peticaotabeladejurosCollectionPeticaotabeladejuros : peticao.getPeticaotabeladejurosCollection()) {
                Peticao oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionPeticaotabeladejuros = peticaotabeladejurosCollectionPeticaotabeladejuros.getPeticaotabeladejurospeticao();
                peticaotabeladejurosCollectionPeticaotabeladejuros.setPeticaotabeladejurospeticao(peticao);
                peticaotabeladejurosCollectionPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionPeticaotabeladejuros);
                if (oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionPeticaotabeladejuros != null) {
                    oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionPeticaotabeladejuros.getPeticaotabeladejurosCollection().remove(peticaotabeladejurosCollectionPeticaotabeladejuros);
                    oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionPeticaotabeladejuros = em.merge(oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionPeticaotabeladejuros);
                }
            }
            for (Processopeticao processopeticaoCollectionProcessopeticao : peticao.getProcessopeticaoCollection()) {
                Peticao oldProcessopeticaopeticaoOfProcessopeticaoCollectionProcessopeticao = processopeticaoCollectionProcessopeticao.getProcessopeticaopeticao();
                processopeticaoCollectionProcessopeticao.setProcessopeticaopeticao(peticao);
                processopeticaoCollectionProcessopeticao = em.merge(processopeticaoCollectionProcessopeticao);
                if (oldProcessopeticaopeticaoOfProcessopeticaoCollectionProcessopeticao != null) {
                    oldProcessopeticaopeticaoOfProcessopeticaoCollectionProcessopeticao.getProcessopeticaoCollection().remove(processopeticaoCollectionProcessopeticao);
                    oldProcessopeticaopeticaoOfProcessopeticaoCollectionProcessopeticao = em.merge(oldProcessopeticaopeticaoOfProcessopeticaoCollectionProcessopeticao);
                }
            }
            for (Peticaopessoa peticaopessoaCollectionPeticaopessoa : peticao.getPeticaopessoaCollection()) {
                Peticao oldPeticaopessoapeticaoOfPeticaopessoaCollectionPeticaopessoa = peticaopessoaCollectionPeticaopessoa.getPeticaopessoapeticao();
                peticaopessoaCollectionPeticaopessoa.setPeticaopessoapeticao(peticao);
                peticaopessoaCollectionPeticaopessoa = em.merge(peticaopessoaCollectionPeticaopessoa);
                if (oldPeticaopessoapeticaoOfPeticaopessoaCollectionPeticaopessoa != null) {
                    oldPeticaopessoapeticaoOfPeticaopessoaCollectionPeticaopessoa.getPeticaopessoaCollection().remove(peticaopessoaCollectionPeticaopessoa);
                    oldPeticaopessoapeticaoOfPeticaopessoaCollectionPeticaopessoa = em.merge(oldPeticaopessoapeticaoOfPeticaopessoaCollectionPeticaopessoa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Peticao peticao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Peticao persistentPeticao = em.find(Peticao.class, peticao.getId());
            Collection<Peticaotabeladejuros> peticaotabeladejurosCollectionOld = persistentPeticao.getPeticaotabeladejurosCollection();
            Collection<Peticaotabeladejuros> peticaotabeladejurosCollectionNew = peticao.getPeticaotabeladejurosCollection();
            Collection<Processopeticao> processopeticaoCollectionOld = persistentPeticao.getProcessopeticaoCollection();
            Collection<Processopeticao> processopeticaoCollectionNew = peticao.getProcessopeticaoCollection();
            Collection<Peticaopessoa> peticaopessoaCollectionOld = persistentPeticao.getPeticaopessoaCollection();
            Collection<Peticaopessoa> peticaopessoaCollectionNew = peticao.getPeticaopessoaCollection();
            List<String> illegalOrphanMessages = null;
            for (Processopeticao processopeticaoCollectionOldProcessopeticao : processopeticaoCollectionOld) {
                if (!processopeticaoCollectionNew.contains(processopeticaoCollectionOldProcessopeticao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Processopeticao " + processopeticaoCollectionOldProcessopeticao + " since its processopeticaopeticao field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Peticaotabeladejuros> attachedPeticaotabeladejurosCollectionNew = new ArrayList<Peticaotabeladejuros>();
            for (Peticaotabeladejuros peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach : peticaotabeladejurosCollectionNew) {
                peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach = em.getReference(peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach.getClass(), peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach.getId());
                attachedPeticaotabeladejurosCollectionNew.add(peticaotabeladejurosCollectionNewPeticaotabeladejurosToAttach);
            }
            peticaotabeladejurosCollectionNew = attachedPeticaotabeladejurosCollectionNew;
            peticao.setPeticaotabeladejurosCollection(peticaotabeladejurosCollectionNew);
            Collection<Processopeticao> attachedProcessopeticaoCollectionNew = new ArrayList<Processopeticao>();
            for (Processopeticao processopeticaoCollectionNewProcessopeticaoToAttach : processopeticaoCollectionNew) {
                processopeticaoCollectionNewProcessopeticaoToAttach = em.getReference(processopeticaoCollectionNewProcessopeticaoToAttach.getClass(), processopeticaoCollectionNewProcessopeticaoToAttach.getId());
                attachedProcessopeticaoCollectionNew.add(processopeticaoCollectionNewProcessopeticaoToAttach);
            }
            processopeticaoCollectionNew = attachedProcessopeticaoCollectionNew;
            peticao.setProcessopeticaoCollection(processopeticaoCollectionNew);
            Collection<Peticaopessoa> attachedPeticaopessoaCollectionNew = new ArrayList<Peticaopessoa>();
            for (Peticaopessoa peticaopessoaCollectionNewPeticaopessoaToAttach : peticaopessoaCollectionNew) {
                peticaopessoaCollectionNewPeticaopessoaToAttach = em.getReference(peticaopessoaCollectionNewPeticaopessoaToAttach.getClass(), peticaopessoaCollectionNewPeticaopessoaToAttach.getId());
                attachedPeticaopessoaCollectionNew.add(peticaopessoaCollectionNewPeticaopessoaToAttach);
            }
            peticaopessoaCollectionNew = attachedPeticaopessoaCollectionNew;
            peticao.setPeticaopessoaCollection(peticaopessoaCollectionNew);
            peticao = em.merge(peticao);
            for (Peticaotabeladejuros peticaotabeladejurosCollectionOldPeticaotabeladejuros : peticaotabeladejurosCollectionOld) {
                if (!peticaotabeladejurosCollectionNew.contains(peticaotabeladejurosCollectionOldPeticaotabeladejuros)) {
                    peticaotabeladejurosCollectionOldPeticaotabeladejuros.setPeticaotabeladejurospeticao(null);
                    peticaotabeladejurosCollectionOldPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionOldPeticaotabeladejuros);
                }
            }
            for (Peticaotabeladejuros peticaotabeladejurosCollectionNewPeticaotabeladejuros : peticaotabeladejurosCollectionNew) {
                if (!peticaotabeladejurosCollectionOld.contains(peticaotabeladejurosCollectionNewPeticaotabeladejuros)) {
                    Peticao oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros = peticaotabeladejurosCollectionNewPeticaotabeladejuros.getPeticaotabeladejurospeticao();
                    peticaotabeladejurosCollectionNewPeticaotabeladejuros.setPeticaotabeladejurospeticao(peticao);
                    peticaotabeladejurosCollectionNewPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionNewPeticaotabeladejuros);
                    if (oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros != null && !oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros.equals(peticao)) {
                        oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros.getPeticaotabeladejurosCollection().remove(peticaotabeladejurosCollectionNewPeticaotabeladejuros);
                        oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros = em.merge(oldPeticaotabeladejurospeticaoOfPeticaotabeladejurosCollectionNewPeticaotabeladejuros);
                    }
                }
            }
            for (Processopeticao processopeticaoCollectionNewProcessopeticao : processopeticaoCollectionNew) {
                if (!processopeticaoCollectionOld.contains(processopeticaoCollectionNewProcessopeticao)) {
                    Peticao oldProcessopeticaopeticaoOfProcessopeticaoCollectionNewProcessopeticao = processopeticaoCollectionNewProcessopeticao.getProcessopeticaopeticao();
                    processopeticaoCollectionNewProcessopeticao.setProcessopeticaopeticao(peticao);
                    processopeticaoCollectionNewProcessopeticao = em.merge(processopeticaoCollectionNewProcessopeticao);
                    if (oldProcessopeticaopeticaoOfProcessopeticaoCollectionNewProcessopeticao != null && !oldProcessopeticaopeticaoOfProcessopeticaoCollectionNewProcessopeticao.equals(peticao)) {
                        oldProcessopeticaopeticaoOfProcessopeticaoCollectionNewProcessopeticao.getProcessopeticaoCollection().remove(processopeticaoCollectionNewProcessopeticao);
                        oldProcessopeticaopeticaoOfProcessopeticaoCollectionNewProcessopeticao = em.merge(oldProcessopeticaopeticaoOfProcessopeticaoCollectionNewProcessopeticao);
                    }
                }
            }
            for (Peticaopessoa peticaopessoaCollectionOldPeticaopessoa : peticaopessoaCollectionOld) {
                if (!peticaopessoaCollectionNew.contains(peticaopessoaCollectionOldPeticaopessoa)) {
                    peticaopessoaCollectionOldPeticaopessoa.setPeticaopessoapeticao(null);
                    peticaopessoaCollectionOldPeticaopessoa = em.merge(peticaopessoaCollectionOldPeticaopessoa);
                }
            }
            for (Peticaopessoa peticaopessoaCollectionNewPeticaopessoa : peticaopessoaCollectionNew) {
                if (!peticaopessoaCollectionOld.contains(peticaopessoaCollectionNewPeticaopessoa)) {
                    Peticao oldPeticaopessoapeticaoOfPeticaopessoaCollectionNewPeticaopessoa = peticaopessoaCollectionNewPeticaopessoa.getPeticaopessoapeticao();
                    peticaopessoaCollectionNewPeticaopessoa.setPeticaopessoapeticao(peticao);
                    peticaopessoaCollectionNewPeticaopessoa = em.merge(peticaopessoaCollectionNewPeticaopessoa);
                    if (oldPeticaopessoapeticaoOfPeticaopessoaCollectionNewPeticaopessoa != null && !oldPeticaopessoapeticaoOfPeticaopessoaCollectionNewPeticaopessoa.equals(peticao)) {
                        oldPeticaopessoapeticaoOfPeticaopessoaCollectionNewPeticaopessoa.getPeticaopessoaCollection().remove(peticaopessoaCollectionNewPeticaopessoa);
                        oldPeticaopessoapeticaoOfPeticaopessoaCollectionNewPeticaopessoa = em.merge(oldPeticaopessoapeticaoOfPeticaopessoaCollectionNewPeticaopessoa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = peticao.getId();
                if (findPeticao(id) == null) {
                    throw new NonexistentEntityException("The peticao with id " + id + " no longer exists.");
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
            Peticao peticao;
            try {
                peticao = em.getReference(Peticao.class, id);
                peticao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The peticao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Processopeticao> processopeticaoCollectionOrphanCheck = peticao.getProcessopeticaoCollection();
            for (Processopeticao processopeticaoCollectionOrphanCheckProcessopeticao : processopeticaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Peticao (" + peticao + ") cannot be destroyed since the Processopeticao " + processopeticaoCollectionOrphanCheckProcessopeticao + " in its processopeticaoCollection field has a non-nullable processopeticaopeticao field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Peticaotabeladejuros> peticaotabeladejurosCollection = peticao.getPeticaotabeladejurosCollection();
            for (Peticaotabeladejuros peticaotabeladejurosCollectionPeticaotabeladejuros : peticaotabeladejurosCollection) {
                peticaotabeladejurosCollectionPeticaotabeladejuros.setPeticaotabeladejurospeticao(null);
                peticaotabeladejurosCollectionPeticaotabeladejuros = em.merge(peticaotabeladejurosCollectionPeticaotabeladejuros);
            }
            Collection<Peticaopessoa> peticaopessoaCollection = peticao.getPeticaopessoaCollection();
            for (Peticaopessoa peticaopessoaCollectionPeticaopessoa : peticaopessoaCollection) {
                peticaopessoaCollectionPeticaopessoa.setPeticaopessoapeticao(null);
                peticaopessoaCollectionPeticaopessoa = em.merge(peticaopessoaCollectionPeticaopessoa);
            }
            em.remove(peticao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Peticao> findPeticaoEntities() {
        return findPeticaoEntities(true, -1, -1);
    }

    public List<Peticao> findPeticaoEntities(int maxResults, int firstResult) {
        return findPeticaoEntities(false, maxResults, firstResult);
    }

    private List<Peticao> findPeticaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Peticao.class));
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

    public Peticao findPeticao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Peticao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeticaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Peticao> rt = cq.from(Peticao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

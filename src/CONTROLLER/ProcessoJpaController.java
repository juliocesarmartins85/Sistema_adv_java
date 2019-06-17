/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Jurisprudencia;
import ENTIDADES.Processopessoa;
import ENTIDADES.Processopeticao;
import ENTIDADES.Processoaudiencia;
import ENTIDADES.Processo;
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
public class ProcessoJpaController implements Serializable {

    public ProcessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Processo processo) {
        if (processo.getProcessopeticaoCollection() == null) {
            processo.setProcessopeticaoCollection(new ArrayList<Processopeticao>());
        }
        if (processo.getProcessopessoaCollection() == null) {
            processo.setProcessopessoaCollection(new ArrayList<Processopessoa>());
        }
        if (processo.getProcessoaudienciaCollection() == null) {
            processo.setProcessoaudienciaCollection(new ArrayList<Processoaudiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jurisprudencia processojurisprudencia = processo.getProcessojurisprudencia();
            if (processojurisprudencia != null) {
                processojurisprudencia = em.getReference(processojurisprudencia.getClass(), processojurisprudencia.getId());
                processo.setProcessojurisprudencia(processojurisprudencia);
            }
            Collection<Processopeticao> attachedProcessopeticaoCollection = new ArrayList<Processopeticao>();
            for (Processopeticao processopeticaoCollectionProcessopeticaoToAttach : processo.getProcessopeticaoCollection()) {
                processopeticaoCollectionProcessopeticaoToAttach = em.getReference(processopeticaoCollectionProcessopeticaoToAttach.getClass(), processopeticaoCollectionProcessopeticaoToAttach.getId());
                attachedProcessopeticaoCollection.add(processopeticaoCollectionProcessopeticaoToAttach);
            }
            processo.setProcessopeticaoCollection(attachedProcessopeticaoCollection);
            Collection<Processopessoa> attachedProcessopessoaCollection = new ArrayList<Processopessoa>();
            for (Processopessoa processopessoaCollectionProcessopessoaToAttach : processo.getProcessopessoaCollection()) {
                processopessoaCollectionProcessopessoaToAttach = em.getReference(processopessoaCollectionProcessopessoaToAttach.getClass(), processopessoaCollectionProcessopessoaToAttach.getId());
                attachedProcessopessoaCollection.add(processopessoaCollectionProcessopessoaToAttach);
            }
            processo.setProcessopessoaCollection(attachedProcessopessoaCollection);
            Collection<Processoaudiencia> attachedProcessoaudienciaCollection = new ArrayList<Processoaudiencia>();
            for (Processoaudiencia processoaudienciaCollectionProcessoaudienciaToAttach : processo.getProcessoaudienciaCollection()) {
                processoaudienciaCollectionProcessoaudienciaToAttach = em.getReference(processoaudienciaCollectionProcessoaudienciaToAttach.getClass(), processoaudienciaCollectionProcessoaudienciaToAttach.getId());
                attachedProcessoaudienciaCollection.add(processoaudienciaCollectionProcessoaudienciaToAttach);
            }
            processo.setProcessoaudienciaCollection(attachedProcessoaudienciaCollection);
            em.persist(processo);
            if (processojurisprudencia != null) {
                processojurisprudencia.getProcessoCollection().add(processo);
                processojurisprudencia = em.merge(processojurisprudencia);
            }
            for (Processopeticao processopeticaoCollectionProcessopeticao : processo.getProcessopeticaoCollection()) {
                Processo oldProcessopeticaoprocessoOfProcessopeticaoCollectionProcessopeticao = processopeticaoCollectionProcessopeticao.getProcessopeticaoprocesso();
                processopeticaoCollectionProcessopeticao.setProcessopeticaoprocesso(processo);
                processopeticaoCollectionProcessopeticao = em.merge(processopeticaoCollectionProcessopeticao);
                if (oldProcessopeticaoprocessoOfProcessopeticaoCollectionProcessopeticao != null) {
                    oldProcessopeticaoprocessoOfProcessopeticaoCollectionProcessopeticao.getProcessopeticaoCollection().remove(processopeticaoCollectionProcessopeticao);
                    oldProcessopeticaoprocessoOfProcessopeticaoCollectionProcessopeticao = em.merge(oldProcessopeticaoprocessoOfProcessopeticaoCollectionProcessopeticao);
                }
            }
            for (Processopessoa processopessoaCollectionProcessopessoa : processo.getProcessopessoaCollection()) {
                Processo oldProcessopessoaprocessoOfProcessopessoaCollectionProcessopessoa = processopessoaCollectionProcessopessoa.getProcessopessoaprocesso();
                processopessoaCollectionProcessopessoa.setProcessopessoaprocesso(processo);
                processopessoaCollectionProcessopessoa = em.merge(processopessoaCollectionProcessopessoa);
                if (oldProcessopessoaprocessoOfProcessopessoaCollectionProcessopessoa != null) {
                    oldProcessopessoaprocessoOfProcessopessoaCollectionProcessopessoa.getProcessopessoaCollection().remove(processopessoaCollectionProcessopessoa);
                    oldProcessopessoaprocessoOfProcessopessoaCollectionProcessopessoa = em.merge(oldProcessopessoaprocessoOfProcessopessoaCollectionProcessopessoa);
                }
            }
            for (Processoaudiencia processoaudienciaCollectionProcessoaudiencia : processo.getProcessoaudienciaCollection()) {
                Processo oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionProcessoaudiencia = processoaudienciaCollectionProcessoaudiencia.getProcessoaudienciaprocesso();
                processoaudienciaCollectionProcessoaudiencia.setProcessoaudienciaprocesso(processo);
                processoaudienciaCollectionProcessoaudiencia = em.merge(processoaudienciaCollectionProcessoaudiencia);
                if (oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionProcessoaudiencia != null) {
                    oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionProcessoaudiencia.getProcessoaudienciaCollection().remove(processoaudienciaCollectionProcessoaudiencia);
                    oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionProcessoaudiencia = em.merge(oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionProcessoaudiencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processo processo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo persistentProcesso = em.find(Processo.class, processo.getId());
            Jurisprudencia processojurisprudenciaOld = persistentProcesso.getProcessojurisprudencia();
            Jurisprudencia processojurisprudenciaNew = processo.getProcessojurisprudencia();
            Collection<Processopeticao> processopeticaoCollectionOld = persistentProcesso.getProcessopeticaoCollection();
            Collection<Processopeticao> processopeticaoCollectionNew = processo.getProcessopeticaoCollection();
            Collection<Processopessoa> processopessoaCollectionOld = persistentProcesso.getProcessopessoaCollection();
            Collection<Processopessoa> processopessoaCollectionNew = processo.getProcessopessoaCollection();
            Collection<Processoaudiencia> processoaudienciaCollectionOld = persistentProcesso.getProcessoaudienciaCollection();
            Collection<Processoaudiencia> processoaudienciaCollectionNew = processo.getProcessoaudienciaCollection();
            List<String> illegalOrphanMessages = null;
            for (Processopeticao processopeticaoCollectionOldProcessopeticao : processopeticaoCollectionOld) {
                if (!processopeticaoCollectionNew.contains(processopeticaoCollectionOldProcessopeticao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Processopeticao " + processopeticaoCollectionOldProcessopeticao + " since its processopeticaoprocesso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (processojurisprudenciaNew != null) {
                processojurisprudenciaNew = em.getReference(processojurisprudenciaNew.getClass(), processojurisprudenciaNew.getId());
                processo.setProcessojurisprudencia(processojurisprudenciaNew);
            }
            Collection<Processopeticao> attachedProcessopeticaoCollectionNew = new ArrayList<Processopeticao>();
            for (Processopeticao processopeticaoCollectionNewProcessopeticaoToAttach : processopeticaoCollectionNew) {
                processopeticaoCollectionNewProcessopeticaoToAttach = em.getReference(processopeticaoCollectionNewProcessopeticaoToAttach.getClass(), processopeticaoCollectionNewProcessopeticaoToAttach.getId());
                attachedProcessopeticaoCollectionNew.add(processopeticaoCollectionNewProcessopeticaoToAttach);
            }
            processopeticaoCollectionNew = attachedProcessopeticaoCollectionNew;
            processo.setProcessopeticaoCollection(processopeticaoCollectionNew);
            Collection<Processopessoa> attachedProcessopessoaCollectionNew = new ArrayList<Processopessoa>();
            for (Processopessoa processopessoaCollectionNewProcessopessoaToAttach : processopessoaCollectionNew) {
                processopessoaCollectionNewProcessopessoaToAttach = em.getReference(processopessoaCollectionNewProcessopessoaToAttach.getClass(), processopessoaCollectionNewProcessopessoaToAttach.getId());
                attachedProcessopessoaCollectionNew.add(processopessoaCollectionNewProcessopessoaToAttach);
            }
            processopessoaCollectionNew = attachedProcessopessoaCollectionNew;
            processo.setProcessopessoaCollection(processopessoaCollectionNew);
            Collection<Processoaudiencia> attachedProcessoaudienciaCollectionNew = new ArrayList<Processoaudiencia>();
            for (Processoaudiencia processoaudienciaCollectionNewProcessoaudienciaToAttach : processoaudienciaCollectionNew) {
                processoaudienciaCollectionNewProcessoaudienciaToAttach = em.getReference(processoaudienciaCollectionNewProcessoaudienciaToAttach.getClass(), processoaudienciaCollectionNewProcessoaudienciaToAttach.getId());
                attachedProcessoaudienciaCollectionNew.add(processoaudienciaCollectionNewProcessoaudienciaToAttach);
            }
            processoaudienciaCollectionNew = attachedProcessoaudienciaCollectionNew;
            processo.setProcessoaudienciaCollection(processoaudienciaCollectionNew);
            processo = em.merge(processo);
            if (processojurisprudenciaOld != null && !processojurisprudenciaOld.equals(processojurisprudenciaNew)) {
                processojurisprudenciaOld.getProcessoCollection().remove(processo);
                processojurisprudenciaOld = em.merge(processojurisprudenciaOld);
            }
            if (processojurisprudenciaNew != null && !processojurisprudenciaNew.equals(processojurisprudenciaOld)) {
                processojurisprudenciaNew.getProcessoCollection().add(processo);
                processojurisprudenciaNew = em.merge(processojurisprudenciaNew);
            }
            for (Processopeticao processopeticaoCollectionNewProcessopeticao : processopeticaoCollectionNew) {
                if (!processopeticaoCollectionOld.contains(processopeticaoCollectionNewProcessopeticao)) {
                    Processo oldProcessopeticaoprocessoOfProcessopeticaoCollectionNewProcessopeticao = processopeticaoCollectionNewProcessopeticao.getProcessopeticaoprocesso();
                    processopeticaoCollectionNewProcessopeticao.setProcessopeticaoprocesso(processo);
                    processopeticaoCollectionNewProcessopeticao = em.merge(processopeticaoCollectionNewProcessopeticao);
                    if (oldProcessopeticaoprocessoOfProcessopeticaoCollectionNewProcessopeticao != null && !oldProcessopeticaoprocessoOfProcessopeticaoCollectionNewProcessopeticao.equals(processo)) {
                        oldProcessopeticaoprocessoOfProcessopeticaoCollectionNewProcessopeticao.getProcessopeticaoCollection().remove(processopeticaoCollectionNewProcessopeticao);
                        oldProcessopeticaoprocessoOfProcessopeticaoCollectionNewProcessopeticao = em.merge(oldProcessopeticaoprocessoOfProcessopeticaoCollectionNewProcessopeticao);
                    }
                }
            }
            for (Processopessoa processopessoaCollectionOldProcessopessoa : processopessoaCollectionOld) {
                if (!processopessoaCollectionNew.contains(processopessoaCollectionOldProcessopessoa)) {
                    processopessoaCollectionOldProcessopessoa.setProcessopessoaprocesso(null);
                    processopessoaCollectionOldProcessopessoa = em.merge(processopessoaCollectionOldProcessopessoa);
                }
            }
            for (Processopessoa processopessoaCollectionNewProcessopessoa : processopessoaCollectionNew) {
                if (!processopessoaCollectionOld.contains(processopessoaCollectionNewProcessopessoa)) {
                    Processo oldProcessopessoaprocessoOfProcessopessoaCollectionNewProcessopessoa = processopessoaCollectionNewProcessopessoa.getProcessopessoaprocesso();
                    processopessoaCollectionNewProcessopessoa.setProcessopessoaprocesso(processo);
                    processopessoaCollectionNewProcessopessoa = em.merge(processopessoaCollectionNewProcessopessoa);
                    if (oldProcessopessoaprocessoOfProcessopessoaCollectionNewProcessopessoa != null && !oldProcessopessoaprocessoOfProcessopessoaCollectionNewProcessopessoa.equals(processo)) {
                        oldProcessopessoaprocessoOfProcessopessoaCollectionNewProcessopessoa.getProcessopessoaCollection().remove(processopessoaCollectionNewProcessopessoa);
                        oldProcessopessoaprocessoOfProcessopessoaCollectionNewProcessopessoa = em.merge(oldProcessopessoaprocessoOfProcessopessoaCollectionNewProcessopessoa);
                    }
                }
            }
            for (Processoaudiencia processoaudienciaCollectionOldProcessoaudiencia : processoaudienciaCollectionOld) {
                if (!processoaudienciaCollectionNew.contains(processoaudienciaCollectionOldProcessoaudiencia)) {
                    processoaudienciaCollectionOldProcessoaudiencia.setProcessoaudienciaprocesso(null);
                    processoaudienciaCollectionOldProcessoaudiencia = em.merge(processoaudienciaCollectionOldProcessoaudiencia);
                }
            }
            for (Processoaudiencia processoaudienciaCollectionNewProcessoaudiencia : processoaudienciaCollectionNew) {
                if (!processoaudienciaCollectionOld.contains(processoaudienciaCollectionNewProcessoaudiencia)) {
                    Processo oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionNewProcessoaudiencia = processoaudienciaCollectionNewProcessoaudiencia.getProcessoaudienciaprocesso();
                    processoaudienciaCollectionNewProcessoaudiencia.setProcessoaudienciaprocesso(processo);
                    processoaudienciaCollectionNewProcessoaudiencia = em.merge(processoaudienciaCollectionNewProcessoaudiencia);
                    if (oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionNewProcessoaudiencia != null && !oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionNewProcessoaudiencia.equals(processo)) {
                        oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionNewProcessoaudiencia.getProcessoaudienciaCollection().remove(processoaudienciaCollectionNewProcessoaudiencia);
                        oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionNewProcessoaudiencia = em.merge(oldProcessoaudienciaprocessoOfProcessoaudienciaCollectionNewProcessoaudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processo.getId();
                if (findProcesso(id) == null) {
                    throw new NonexistentEntityException("The processo with id " + id + " no longer exists.");
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
            Processo processo;
            try {
                processo = em.getReference(Processo.class, id);
                processo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Processopeticao> processopeticaoCollectionOrphanCheck = processo.getProcessopeticaoCollection();
            for (Processopeticao processopeticaoCollectionOrphanCheckProcessopeticao : processopeticaoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Processo (" + processo + ") cannot be destroyed since the Processopeticao " + processopeticaoCollectionOrphanCheckProcessopeticao + " in its processopeticaoCollection field has a non-nullable processopeticaoprocesso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Jurisprudencia processojurisprudencia = processo.getProcessojurisprudencia();
            if (processojurisprudencia != null) {
                processojurisprudencia.getProcessoCollection().remove(processo);
                processojurisprudencia = em.merge(processojurisprudencia);
            }
            Collection<Processopessoa> processopessoaCollection = processo.getProcessopessoaCollection();
            for (Processopessoa processopessoaCollectionProcessopessoa : processopessoaCollection) {
                processopessoaCollectionProcessopessoa.setProcessopessoaprocesso(null);
                processopessoaCollectionProcessopessoa = em.merge(processopessoaCollectionProcessopessoa);
            }
            Collection<Processoaudiencia> processoaudienciaCollection = processo.getProcessoaudienciaCollection();
            for (Processoaudiencia processoaudienciaCollectionProcessoaudiencia : processoaudienciaCollection) {
                processoaudienciaCollectionProcessoaudiencia.setProcessoaudienciaprocesso(null);
                processoaudienciaCollectionProcessoaudiencia = em.merge(processoaudienciaCollectionProcessoaudiencia);
            }
            em.remove(processo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Processo> findProcessoEntities() {
        return findProcessoEntities(true, -1, -1);
    }

    public List<Processo> findProcessoEntities(int maxResults, int firstResult) {
        return findProcessoEntities(false, maxResults, firstResult);
    }

    private List<Processo> findProcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Processo.class));
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

    public Processo findProcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Processo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Processo> rt = cq.from(Processo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

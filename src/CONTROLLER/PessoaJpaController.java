/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import ENTIDADES.Procuracao;
import ENTIDADES.Contrato;
import ENTIDADES.Advogado;
import ENTIDADES.Peticaopessoa;
import ENTIDADES.Juridica;
import ENTIDADES.Pessoa;
import ENTIDADES.Rua;
import ENTIDADES.Processopessoa;
import ENTIDADES.Fisica;
import ENTIDADES.Telefone;
import ENTIDADES.Contratopessoa;
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
public class PessoaJpaController implements Serializable {

    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pessoa pessoa) {
        if (pessoa.getProcuracaoCollection() == null) {
            pessoa.setProcuracaoCollection(new ArrayList<Procuracao>());
        }
        if (pessoa.getTelefoneCollection() == null) {
            pessoa.setTelefoneCollection(new ArrayList<Telefone>());
        }
        if (pessoa.getPeticaopessoaCollection() == null) {
            pessoa.setPeticaopessoaCollection(new ArrayList<Peticaopessoa>());
        }
        if (pessoa.getAdvogadoCollection() == null) {
            pessoa.setAdvogadoCollection(new ArrayList<Advogado>());
        }
        if (pessoa.getProcessopessoaCollection() == null) {
            pessoa.setProcessopessoaCollection(new ArrayList<Processopessoa>());
        }
        if (pessoa.getContratoCollection() == null) {
            pessoa.setContratoCollection(new ArrayList<Contrato>());
        }
        if (pessoa.getContratopessoaCollection() == null) {
            pessoa.setContratopessoaCollection(new ArrayList<Contratopessoa>());
        }
        if (pessoa.getJuridicaCollection() == null) {
            pessoa.setJuridicaCollection(new ArrayList<Juridica>());
        }
        if (pessoa.getFisicaCollection() == null) {
            pessoa.setFisicaCollection(new ArrayList<Fisica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rua pessoarua = pessoa.getPessoarua();
            if (pessoarua != null) {
                pessoarua = em.getReference(pessoarua.getClass(), pessoarua.getId());
                pessoa.setPessoarua(pessoarua);
            }
            Collection<Procuracao> attachedProcuracaoCollection = new ArrayList<Procuracao>();
            for (Procuracao procuracaoCollectionProcuracaoToAttach : pessoa.getProcuracaoCollection()) {
                procuracaoCollectionProcuracaoToAttach = em.getReference(procuracaoCollectionProcuracaoToAttach.getClass(), procuracaoCollectionProcuracaoToAttach.getId());
                attachedProcuracaoCollection.add(procuracaoCollectionProcuracaoToAttach);
            }
            pessoa.setProcuracaoCollection(attachedProcuracaoCollection);
            Collection<Telefone> attachedTelefoneCollection = new ArrayList<Telefone>();
            for (Telefone telefoneCollectionTelefoneToAttach : pessoa.getTelefoneCollection()) {
                telefoneCollectionTelefoneToAttach = em.getReference(telefoneCollectionTelefoneToAttach.getClass(), telefoneCollectionTelefoneToAttach.getId());
                attachedTelefoneCollection.add(telefoneCollectionTelefoneToAttach);
            }
            pessoa.setTelefoneCollection(attachedTelefoneCollection);
            Collection<Peticaopessoa> attachedPeticaopessoaCollection = new ArrayList<Peticaopessoa>();
            for (Peticaopessoa peticaopessoaCollectionPeticaopessoaToAttach : pessoa.getPeticaopessoaCollection()) {
                peticaopessoaCollectionPeticaopessoaToAttach = em.getReference(peticaopessoaCollectionPeticaopessoaToAttach.getClass(), peticaopessoaCollectionPeticaopessoaToAttach.getId());
                attachedPeticaopessoaCollection.add(peticaopessoaCollectionPeticaopessoaToAttach);
            }
            pessoa.setPeticaopessoaCollection(attachedPeticaopessoaCollection);
            Collection<Advogado> attachedAdvogadoCollection = new ArrayList<Advogado>();
            for (Advogado advogadoCollectionAdvogadoToAttach : pessoa.getAdvogadoCollection()) {
                advogadoCollectionAdvogadoToAttach = em.getReference(advogadoCollectionAdvogadoToAttach.getClass(), advogadoCollectionAdvogadoToAttach.getId());
                attachedAdvogadoCollection.add(advogadoCollectionAdvogadoToAttach);
            }
            pessoa.setAdvogadoCollection(attachedAdvogadoCollection);
            Collection<Processopessoa> attachedProcessopessoaCollection = new ArrayList<Processopessoa>();
            for (Processopessoa processopessoaCollectionProcessopessoaToAttach : pessoa.getProcessopessoaCollection()) {
                processopessoaCollectionProcessopessoaToAttach = em.getReference(processopessoaCollectionProcessopessoaToAttach.getClass(), processopessoaCollectionProcessopessoaToAttach.getId());
                attachedProcessopessoaCollection.add(processopessoaCollectionProcessopessoaToAttach);
            }
            pessoa.setProcessopessoaCollection(attachedProcessopessoaCollection);
            Collection<Contrato> attachedContratoCollection = new ArrayList<Contrato>();
            for (Contrato contratoCollectionContratoToAttach : pessoa.getContratoCollection()) {
                contratoCollectionContratoToAttach = em.getReference(contratoCollectionContratoToAttach.getClass(), contratoCollectionContratoToAttach.getId());
                attachedContratoCollection.add(contratoCollectionContratoToAttach);
            }
            pessoa.setContratoCollection(attachedContratoCollection);
            Collection<Contratopessoa> attachedContratopessoaCollection = new ArrayList<Contratopessoa>();
            for (Contratopessoa contratopessoaCollectionContratopessoaToAttach : pessoa.getContratopessoaCollection()) {
                contratopessoaCollectionContratopessoaToAttach = em.getReference(contratopessoaCollectionContratopessoaToAttach.getClass(), contratopessoaCollectionContratopessoaToAttach.getId());
                attachedContratopessoaCollection.add(contratopessoaCollectionContratopessoaToAttach);
            }
            pessoa.setContratopessoaCollection(attachedContratopessoaCollection);
            Collection<Juridica> attachedJuridicaCollection = new ArrayList<Juridica>();
            for (Juridica juridicaCollectionJuridicaToAttach : pessoa.getJuridicaCollection()) {
                juridicaCollectionJuridicaToAttach = em.getReference(juridicaCollectionJuridicaToAttach.getClass(), juridicaCollectionJuridicaToAttach.getId());
                attachedJuridicaCollection.add(juridicaCollectionJuridicaToAttach);
            }
            pessoa.setJuridicaCollection(attachedJuridicaCollection);
            Collection<Fisica> attachedFisicaCollection = new ArrayList<Fisica>();
            for (Fisica fisicaCollectionFisicaToAttach : pessoa.getFisicaCollection()) {
                fisicaCollectionFisicaToAttach = em.getReference(fisicaCollectionFisicaToAttach.getClass(), fisicaCollectionFisicaToAttach.getId());
                attachedFisicaCollection.add(fisicaCollectionFisicaToAttach);
            }
            pessoa.setFisicaCollection(attachedFisicaCollection);
            em.persist(pessoa);
            if (pessoarua != null) {
                pessoarua.getPessoaCollection().add(pessoa);
                pessoarua = em.merge(pessoarua);
            }
            for (Procuracao procuracaoCollectionProcuracao : pessoa.getProcuracaoCollection()) {
                Pessoa oldProcuracaopessoaOfProcuracaoCollectionProcuracao = procuracaoCollectionProcuracao.getProcuracaopessoa();
                procuracaoCollectionProcuracao.setProcuracaopessoa(pessoa);
                procuracaoCollectionProcuracao = em.merge(procuracaoCollectionProcuracao);
                if (oldProcuracaopessoaOfProcuracaoCollectionProcuracao != null) {
                    oldProcuracaopessoaOfProcuracaoCollectionProcuracao.getProcuracaoCollection().remove(procuracaoCollectionProcuracao);
                    oldProcuracaopessoaOfProcuracaoCollectionProcuracao = em.merge(oldProcuracaopessoaOfProcuracaoCollectionProcuracao);
                }
            }
            for (Telefone telefoneCollectionTelefone : pessoa.getTelefoneCollection()) {
                Pessoa oldTelefonepessoaOfTelefoneCollectionTelefone = telefoneCollectionTelefone.getTelefonepessoa();
                telefoneCollectionTelefone.setTelefonepessoa(pessoa);
                telefoneCollectionTelefone = em.merge(telefoneCollectionTelefone);
                if (oldTelefonepessoaOfTelefoneCollectionTelefone != null) {
                    oldTelefonepessoaOfTelefoneCollectionTelefone.getTelefoneCollection().remove(telefoneCollectionTelefone);
                    oldTelefonepessoaOfTelefoneCollectionTelefone = em.merge(oldTelefonepessoaOfTelefoneCollectionTelefone);
                }
            }
            for (Peticaopessoa peticaopessoaCollectionPeticaopessoa : pessoa.getPeticaopessoaCollection()) {
                Pessoa oldPeticaopessoapesssoaOfPeticaopessoaCollectionPeticaopessoa = peticaopessoaCollectionPeticaopessoa.getPeticaopessoapesssoa();
                peticaopessoaCollectionPeticaopessoa.setPeticaopessoapesssoa(pessoa);
                peticaopessoaCollectionPeticaopessoa = em.merge(peticaopessoaCollectionPeticaopessoa);
                if (oldPeticaopessoapesssoaOfPeticaopessoaCollectionPeticaopessoa != null) {
                    oldPeticaopessoapesssoaOfPeticaopessoaCollectionPeticaopessoa.getPeticaopessoaCollection().remove(peticaopessoaCollectionPeticaopessoa);
                    oldPeticaopessoapesssoaOfPeticaopessoaCollectionPeticaopessoa = em.merge(oldPeticaopessoapesssoaOfPeticaopessoaCollectionPeticaopessoa);
                }
            }
            for (Advogado advogadoCollectionAdvogado : pessoa.getAdvogadoCollection()) {
                Pessoa oldAdvogadopessoaOfAdvogadoCollectionAdvogado = advogadoCollectionAdvogado.getAdvogadopessoa();
                advogadoCollectionAdvogado.setAdvogadopessoa(pessoa);
                advogadoCollectionAdvogado = em.merge(advogadoCollectionAdvogado);
                if (oldAdvogadopessoaOfAdvogadoCollectionAdvogado != null) {
                    oldAdvogadopessoaOfAdvogadoCollectionAdvogado.getAdvogadoCollection().remove(advogadoCollectionAdvogado);
                    oldAdvogadopessoaOfAdvogadoCollectionAdvogado = em.merge(oldAdvogadopessoaOfAdvogadoCollectionAdvogado);
                }
            }
            for (Processopessoa processopessoaCollectionProcessopessoa : pessoa.getProcessopessoaCollection()) {
                Pessoa oldProcessopessoapessoaOfProcessopessoaCollectionProcessopessoa = processopessoaCollectionProcessopessoa.getProcessopessoapessoa();
                processopessoaCollectionProcessopessoa.setProcessopessoapessoa(pessoa);
                processopessoaCollectionProcessopessoa = em.merge(processopessoaCollectionProcessopessoa);
                if (oldProcessopessoapessoaOfProcessopessoaCollectionProcessopessoa != null) {
                    oldProcessopessoapessoaOfProcessopessoaCollectionProcessopessoa.getProcessopessoaCollection().remove(processopessoaCollectionProcessopessoa);
                    oldProcessopessoapessoaOfProcessopessoaCollectionProcessopessoa = em.merge(oldProcessopessoapessoaOfProcessopessoaCollectionProcessopessoa);
                }
            }
            for (Contrato contratoCollectionContrato : pessoa.getContratoCollection()) {
                Pessoa oldContratopessoaOfContratoCollectionContrato = contratoCollectionContrato.getContratopessoa();
                contratoCollectionContrato.setContratopessoa(pessoa);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
                if (oldContratopessoaOfContratoCollectionContrato != null) {
                    oldContratopessoaOfContratoCollectionContrato.getContratoCollection().remove(contratoCollectionContrato);
                    oldContratopessoaOfContratoCollectionContrato = em.merge(oldContratopessoaOfContratoCollectionContrato);
                }
            }
            for (Contratopessoa contratopessoaCollectionContratopessoa : pessoa.getContratopessoaCollection()) {
                Pessoa oldContratopessoapessoaOfContratopessoaCollectionContratopessoa = contratopessoaCollectionContratopessoa.getContratopessoapessoa();
                contratopessoaCollectionContratopessoa.setContratopessoapessoa(pessoa);
                contratopessoaCollectionContratopessoa = em.merge(contratopessoaCollectionContratopessoa);
                if (oldContratopessoapessoaOfContratopessoaCollectionContratopessoa != null) {
                    oldContratopessoapessoaOfContratopessoaCollectionContratopessoa.getContratopessoaCollection().remove(contratopessoaCollectionContratopessoa);
                    oldContratopessoapessoaOfContratopessoaCollectionContratopessoa = em.merge(oldContratopessoapessoaOfContratopessoaCollectionContratopessoa);
                }
            }
            for (Juridica juridicaCollectionJuridica : pessoa.getJuridicaCollection()) {
                Pessoa oldJuridicapessoaOfJuridicaCollectionJuridica = juridicaCollectionJuridica.getJuridicapessoa();
                juridicaCollectionJuridica.setJuridicapessoa(pessoa);
                juridicaCollectionJuridica = em.merge(juridicaCollectionJuridica);
                if (oldJuridicapessoaOfJuridicaCollectionJuridica != null) {
                    oldJuridicapessoaOfJuridicaCollectionJuridica.getJuridicaCollection().remove(juridicaCollectionJuridica);
                    oldJuridicapessoaOfJuridicaCollectionJuridica = em.merge(oldJuridicapessoaOfJuridicaCollectionJuridica);
                }
            }
            for (Fisica fisicaCollectionFisica : pessoa.getFisicaCollection()) {
                Pessoa oldFisicapessoaOfFisicaCollectionFisica = fisicaCollectionFisica.getFisicapessoa();
                fisicaCollectionFisica.setFisicapessoa(pessoa);
                fisicaCollectionFisica = em.merge(fisicaCollectionFisica);
                if (oldFisicapessoaOfFisicaCollectionFisica != null) {
                    oldFisicapessoaOfFisicaCollectionFisica.getFisicaCollection().remove(fisicaCollectionFisica);
                    oldFisicapessoaOfFisicaCollectionFisica = em.merge(oldFisicapessoaOfFisicaCollectionFisica);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pessoa pessoa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pessoa persistentPessoa = em.find(Pessoa.class, pessoa.getId());
            Rua pessoaruaOld = persistentPessoa.getPessoarua();
            Rua pessoaruaNew = pessoa.getPessoarua();
            Collection<Procuracao> procuracaoCollectionOld = persistentPessoa.getProcuracaoCollection();
            Collection<Procuracao> procuracaoCollectionNew = pessoa.getProcuracaoCollection();
            Collection<Telefone> telefoneCollectionOld = persistentPessoa.getTelefoneCollection();
            Collection<Telefone> telefoneCollectionNew = pessoa.getTelefoneCollection();
            Collection<Peticaopessoa> peticaopessoaCollectionOld = persistentPessoa.getPeticaopessoaCollection();
            Collection<Peticaopessoa> peticaopessoaCollectionNew = pessoa.getPeticaopessoaCollection();
            Collection<Advogado> advogadoCollectionOld = persistentPessoa.getAdvogadoCollection();
            Collection<Advogado> advogadoCollectionNew = pessoa.getAdvogadoCollection();
            Collection<Processopessoa> processopessoaCollectionOld = persistentPessoa.getProcessopessoaCollection();
            Collection<Processopessoa> processopessoaCollectionNew = pessoa.getProcessopessoaCollection();
            Collection<Contrato> contratoCollectionOld = persistentPessoa.getContratoCollection();
            Collection<Contrato> contratoCollectionNew = pessoa.getContratoCollection();
            Collection<Contratopessoa> contratopessoaCollectionOld = persistentPessoa.getContratopessoaCollection();
            Collection<Contratopessoa> contratopessoaCollectionNew = pessoa.getContratopessoaCollection();
            Collection<Juridica> juridicaCollectionOld = persistentPessoa.getJuridicaCollection();
            Collection<Juridica> juridicaCollectionNew = pessoa.getJuridicaCollection();
            Collection<Fisica> fisicaCollectionOld = persistentPessoa.getFisicaCollection();
            Collection<Fisica> fisicaCollectionNew = pessoa.getFisicaCollection();
            List<String> illegalOrphanMessages = null;
            for (Advogado advogadoCollectionOldAdvogado : advogadoCollectionOld) {
                if (!advogadoCollectionNew.contains(advogadoCollectionOldAdvogado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Advogado " + advogadoCollectionOldAdvogado + " since its advogadopessoa field is not nullable.");
                }
            }
            for (Contratopessoa contratopessoaCollectionOldContratopessoa : contratopessoaCollectionOld) {
                if (!contratopessoaCollectionNew.contains(contratopessoaCollectionOldContratopessoa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contratopessoa " + contratopessoaCollectionOldContratopessoa + " since its contratopessoapessoa field is not nullable.");
                }
            }
            for (Juridica juridicaCollectionOldJuridica : juridicaCollectionOld) {
                if (!juridicaCollectionNew.contains(juridicaCollectionOldJuridica)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Juridica " + juridicaCollectionOldJuridica + " since its juridicapessoa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pessoaruaNew != null) {
                pessoaruaNew = em.getReference(pessoaruaNew.getClass(), pessoaruaNew.getId());
                pessoa.setPessoarua(pessoaruaNew);
            }
            Collection<Procuracao> attachedProcuracaoCollectionNew = new ArrayList<Procuracao>();
            for (Procuracao procuracaoCollectionNewProcuracaoToAttach : procuracaoCollectionNew) {
                procuracaoCollectionNewProcuracaoToAttach = em.getReference(procuracaoCollectionNewProcuracaoToAttach.getClass(), procuracaoCollectionNewProcuracaoToAttach.getId());
                attachedProcuracaoCollectionNew.add(procuracaoCollectionNewProcuracaoToAttach);
            }
            procuracaoCollectionNew = attachedProcuracaoCollectionNew;
            pessoa.setProcuracaoCollection(procuracaoCollectionNew);
            Collection<Telefone> attachedTelefoneCollectionNew = new ArrayList<Telefone>();
            for (Telefone telefoneCollectionNewTelefoneToAttach : telefoneCollectionNew) {
                telefoneCollectionNewTelefoneToAttach = em.getReference(telefoneCollectionNewTelefoneToAttach.getClass(), telefoneCollectionNewTelefoneToAttach.getId());
                attachedTelefoneCollectionNew.add(telefoneCollectionNewTelefoneToAttach);
            }
            telefoneCollectionNew = attachedTelefoneCollectionNew;
            pessoa.setTelefoneCollection(telefoneCollectionNew);
            Collection<Peticaopessoa> attachedPeticaopessoaCollectionNew = new ArrayList<Peticaopessoa>();
            for (Peticaopessoa peticaopessoaCollectionNewPeticaopessoaToAttach : peticaopessoaCollectionNew) {
                peticaopessoaCollectionNewPeticaopessoaToAttach = em.getReference(peticaopessoaCollectionNewPeticaopessoaToAttach.getClass(), peticaopessoaCollectionNewPeticaopessoaToAttach.getId());
                attachedPeticaopessoaCollectionNew.add(peticaopessoaCollectionNewPeticaopessoaToAttach);
            }
            peticaopessoaCollectionNew = attachedPeticaopessoaCollectionNew;
            pessoa.setPeticaopessoaCollection(peticaopessoaCollectionNew);
            Collection<Advogado> attachedAdvogadoCollectionNew = new ArrayList<Advogado>();
            for (Advogado advogadoCollectionNewAdvogadoToAttach : advogadoCollectionNew) {
                advogadoCollectionNewAdvogadoToAttach = em.getReference(advogadoCollectionNewAdvogadoToAttach.getClass(), advogadoCollectionNewAdvogadoToAttach.getId());
                attachedAdvogadoCollectionNew.add(advogadoCollectionNewAdvogadoToAttach);
            }
            advogadoCollectionNew = attachedAdvogadoCollectionNew;
            pessoa.setAdvogadoCollection(advogadoCollectionNew);
            Collection<Processopessoa> attachedProcessopessoaCollectionNew = new ArrayList<Processopessoa>();
            for (Processopessoa processopessoaCollectionNewProcessopessoaToAttach : processopessoaCollectionNew) {
                processopessoaCollectionNewProcessopessoaToAttach = em.getReference(processopessoaCollectionNewProcessopessoaToAttach.getClass(), processopessoaCollectionNewProcessopessoaToAttach.getId());
                attachedProcessopessoaCollectionNew.add(processopessoaCollectionNewProcessopessoaToAttach);
            }
            processopessoaCollectionNew = attachedProcessopessoaCollectionNew;
            pessoa.setProcessopessoaCollection(processopessoaCollectionNew);
            Collection<Contrato> attachedContratoCollectionNew = new ArrayList<Contrato>();
            for (Contrato contratoCollectionNewContratoToAttach : contratoCollectionNew) {
                contratoCollectionNewContratoToAttach = em.getReference(contratoCollectionNewContratoToAttach.getClass(), contratoCollectionNewContratoToAttach.getId());
                attachedContratoCollectionNew.add(contratoCollectionNewContratoToAttach);
            }
            contratoCollectionNew = attachedContratoCollectionNew;
            pessoa.setContratoCollection(contratoCollectionNew);
            Collection<Contratopessoa> attachedContratopessoaCollectionNew = new ArrayList<Contratopessoa>();
            for (Contratopessoa contratopessoaCollectionNewContratopessoaToAttach : contratopessoaCollectionNew) {
                contratopessoaCollectionNewContratopessoaToAttach = em.getReference(contratopessoaCollectionNewContratopessoaToAttach.getClass(), contratopessoaCollectionNewContratopessoaToAttach.getId());
                attachedContratopessoaCollectionNew.add(contratopessoaCollectionNewContratopessoaToAttach);
            }
            contratopessoaCollectionNew = attachedContratopessoaCollectionNew;
            pessoa.setContratopessoaCollection(contratopessoaCollectionNew);
            Collection<Juridica> attachedJuridicaCollectionNew = new ArrayList<Juridica>();
            for (Juridica juridicaCollectionNewJuridicaToAttach : juridicaCollectionNew) {
                juridicaCollectionNewJuridicaToAttach = em.getReference(juridicaCollectionNewJuridicaToAttach.getClass(), juridicaCollectionNewJuridicaToAttach.getId());
                attachedJuridicaCollectionNew.add(juridicaCollectionNewJuridicaToAttach);
            }
            juridicaCollectionNew = attachedJuridicaCollectionNew;
            pessoa.setJuridicaCollection(juridicaCollectionNew);
            Collection<Fisica> attachedFisicaCollectionNew = new ArrayList<Fisica>();
            for (Fisica fisicaCollectionNewFisicaToAttach : fisicaCollectionNew) {
                fisicaCollectionNewFisicaToAttach = em.getReference(fisicaCollectionNewFisicaToAttach.getClass(), fisicaCollectionNewFisicaToAttach.getId());
                attachedFisicaCollectionNew.add(fisicaCollectionNewFisicaToAttach);
            }
            fisicaCollectionNew = attachedFisicaCollectionNew;
            pessoa.setFisicaCollection(fisicaCollectionNew);
            pessoa = em.merge(pessoa);
            if (pessoaruaOld != null && !pessoaruaOld.equals(pessoaruaNew)) {
                pessoaruaOld.getPessoaCollection().remove(pessoa);
                pessoaruaOld = em.merge(pessoaruaOld);
            }
            if (pessoaruaNew != null && !pessoaruaNew.equals(pessoaruaOld)) {
                pessoaruaNew.getPessoaCollection().add(pessoa);
                pessoaruaNew = em.merge(pessoaruaNew);
            }
            for (Procuracao procuracaoCollectionOldProcuracao : procuracaoCollectionOld) {
                if (!procuracaoCollectionNew.contains(procuracaoCollectionOldProcuracao)) {
                    procuracaoCollectionOldProcuracao.setProcuracaopessoa(null);
                    procuracaoCollectionOldProcuracao = em.merge(procuracaoCollectionOldProcuracao);
                }
            }
            for (Procuracao procuracaoCollectionNewProcuracao : procuracaoCollectionNew) {
                if (!procuracaoCollectionOld.contains(procuracaoCollectionNewProcuracao)) {
                    Pessoa oldProcuracaopessoaOfProcuracaoCollectionNewProcuracao = procuracaoCollectionNewProcuracao.getProcuracaopessoa();
                    procuracaoCollectionNewProcuracao.setProcuracaopessoa(pessoa);
                    procuracaoCollectionNewProcuracao = em.merge(procuracaoCollectionNewProcuracao);
                    if (oldProcuracaopessoaOfProcuracaoCollectionNewProcuracao != null && !oldProcuracaopessoaOfProcuracaoCollectionNewProcuracao.equals(pessoa)) {
                        oldProcuracaopessoaOfProcuracaoCollectionNewProcuracao.getProcuracaoCollection().remove(procuracaoCollectionNewProcuracao);
                        oldProcuracaopessoaOfProcuracaoCollectionNewProcuracao = em.merge(oldProcuracaopessoaOfProcuracaoCollectionNewProcuracao);
                    }
                }
            }
            for (Telefone telefoneCollectionOldTelefone : telefoneCollectionOld) {
                if (!telefoneCollectionNew.contains(telefoneCollectionOldTelefone)) {
                    telefoneCollectionOldTelefone.setTelefonepessoa(null);
                    telefoneCollectionOldTelefone = em.merge(telefoneCollectionOldTelefone);
                }
            }
            for (Telefone telefoneCollectionNewTelefone : telefoneCollectionNew) {
                if (!telefoneCollectionOld.contains(telefoneCollectionNewTelefone)) {
                    Pessoa oldTelefonepessoaOfTelefoneCollectionNewTelefone = telefoneCollectionNewTelefone.getTelefonepessoa();
                    telefoneCollectionNewTelefone.setTelefonepessoa(pessoa);
                    telefoneCollectionNewTelefone = em.merge(telefoneCollectionNewTelefone);
                    if (oldTelefonepessoaOfTelefoneCollectionNewTelefone != null && !oldTelefonepessoaOfTelefoneCollectionNewTelefone.equals(pessoa)) {
                        oldTelefonepessoaOfTelefoneCollectionNewTelefone.getTelefoneCollection().remove(telefoneCollectionNewTelefone);
                        oldTelefonepessoaOfTelefoneCollectionNewTelefone = em.merge(oldTelefonepessoaOfTelefoneCollectionNewTelefone);
                    }
                }
            }
            for (Peticaopessoa peticaopessoaCollectionOldPeticaopessoa : peticaopessoaCollectionOld) {
                if (!peticaopessoaCollectionNew.contains(peticaopessoaCollectionOldPeticaopessoa)) {
                    peticaopessoaCollectionOldPeticaopessoa.setPeticaopessoapesssoa(null);
                    peticaopessoaCollectionOldPeticaopessoa = em.merge(peticaopessoaCollectionOldPeticaopessoa);
                }
            }
            for (Peticaopessoa peticaopessoaCollectionNewPeticaopessoa : peticaopessoaCollectionNew) {
                if (!peticaopessoaCollectionOld.contains(peticaopessoaCollectionNewPeticaopessoa)) {
                    Pessoa oldPeticaopessoapesssoaOfPeticaopessoaCollectionNewPeticaopessoa = peticaopessoaCollectionNewPeticaopessoa.getPeticaopessoapesssoa();
                    peticaopessoaCollectionNewPeticaopessoa.setPeticaopessoapesssoa(pessoa);
                    peticaopessoaCollectionNewPeticaopessoa = em.merge(peticaopessoaCollectionNewPeticaopessoa);
                    if (oldPeticaopessoapesssoaOfPeticaopessoaCollectionNewPeticaopessoa != null && !oldPeticaopessoapesssoaOfPeticaopessoaCollectionNewPeticaopessoa.equals(pessoa)) {
                        oldPeticaopessoapesssoaOfPeticaopessoaCollectionNewPeticaopessoa.getPeticaopessoaCollection().remove(peticaopessoaCollectionNewPeticaopessoa);
                        oldPeticaopessoapesssoaOfPeticaopessoaCollectionNewPeticaopessoa = em.merge(oldPeticaopessoapesssoaOfPeticaopessoaCollectionNewPeticaopessoa);
                    }
                }
            }
            for (Advogado advogadoCollectionNewAdvogado : advogadoCollectionNew) {
                if (!advogadoCollectionOld.contains(advogadoCollectionNewAdvogado)) {
                    Pessoa oldAdvogadopessoaOfAdvogadoCollectionNewAdvogado = advogadoCollectionNewAdvogado.getAdvogadopessoa();
                    advogadoCollectionNewAdvogado.setAdvogadopessoa(pessoa);
                    advogadoCollectionNewAdvogado = em.merge(advogadoCollectionNewAdvogado);
                    if (oldAdvogadopessoaOfAdvogadoCollectionNewAdvogado != null && !oldAdvogadopessoaOfAdvogadoCollectionNewAdvogado.equals(pessoa)) {
                        oldAdvogadopessoaOfAdvogadoCollectionNewAdvogado.getAdvogadoCollection().remove(advogadoCollectionNewAdvogado);
                        oldAdvogadopessoaOfAdvogadoCollectionNewAdvogado = em.merge(oldAdvogadopessoaOfAdvogadoCollectionNewAdvogado);
                    }
                }
            }
            for (Processopessoa processopessoaCollectionOldProcessopessoa : processopessoaCollectionOld) {
                if (!processopessoaCollectionNew.contains(processopessoaCollectionOldProcessopessoa)) {
                    processopessoaCollectionOldProcessopessoa.setProcessopessoapessoa(null);
                    processopessoaCollectionOldProcessopessoa = em.merge(processopessoaCollectionOldProcessopessoa);
                }
            }
            for (Processopessoa processopessoaCollectionNewProcessopessoa : processopessoaCollectionNew) {
                if (!processopessoaCollectionOld.contains(processopessoaCollectionNewProcessopessoa)) {
                    Pessoa oldProcessopessoapessoaOfProcessopessoaCollectionNewProcessopessoa = processopessoaCollectionNewProcessopessoa.getProcessopessoapessoa();
                    processopessoaCollectionNewProcessopessoa.setProcessopessoapessoa(pessoa);
                    processopessoaCollectionNewProcessopessoa = em.merge(processopessoaCollectionNewProcessopessoa);
                    if (oldProcessopessoapessoaOfProcessopessoaCollectionNewProcessopessoa != null && !oldProcessopessoapessoaOfProcessopessoaCollectionNewProcessopessoa.equals(pessoa)) {
                        oldProcessopessoapessoaOfProcessopessoaCollectionNewProcessopessoa.getProcessopessoaCollection().remove(processopessoaCollectionNewProcessopessoa);
                        oldProcessopessoapessoaOfProcessopessoaCollectionNewProcessopessoa = em.merge(oldProcessopessoapessoaOfProcessopessoaCollectionNewProcessopessoa);
                    }
                }
            }
            for (Contrato contratoCollectionOldContrato : contratoCollectionOld) {
                if (!contratoCollectionNew.contains(contratoCollectionOldContrato)) {
                    contratoCollectionOldContrato.setContratopessoa(null);
                    contratoCollectionOldContrato = em.merge(contratoCollectionOldContrato);
                }
            }
            for (Contrato contratoCollectionNewContrato : contratoCollectionNew) {
                if (!contratoCollectionOld.contains(contratoCollectionNewContrato)) {
                    Pessoa oldContratopessoaOfContratoCollectionNewContrato = contratoCollectionNewContrato.getContratopessoa();
                    contratoCollectionNewContrato.setContratopessoa(pessoa);
                    contratoCollectionNewContrato = em.merge(contratoCollectionNewContrato);
                    if (oldContratopessoaOfContratoCollectionNewContrato != null && !oldContratopessoaOfContratoCollectionNewContrato.equals(pessoa)) {
                        oldContratopessoaOfContratoCollectionNewContrato.getContratoCollection().remove(contratoCollectionNewContrato);
                        oldContratopessoaOfContratoCollectionNewContrato = em.merge(oldContratopessoaOfContratoCollectionNewContrato);
                    }
                }
            }
            for (Contratopessoa contratopessoaCollectionNewContratopessoa : contratopessoaCollectionNew) {
                if (!contratopessoaCollectionOld.contains(contratopessoaCollectionNewContratopessoa)) {
                    Pessoa oldContratopessoapessoaOfContratopessoaCollectionNewContratopessoa = contratopessoaCollectionNewContratopessoa.getContratopessoapessoa();
                    contratopessoaCollectionNewContratopessoa.setContratopessoapessoa(pessoa);
                    contratopessoaCollectionNewContratopessoa = em.merge(contratopessoaCollectionNewContratopessoa);
                    if (oldContratopessoapessoaOfContratopessoaCollectionNewContratopessoa != null && !oldContratopessoapessoaOfContratopessoaCollectionNewContratopessoa.equals(pessoa)) {
                        oldContratopessoapessoaOfContratopessoaCollectionNewContratopessoa.getContratopessoaCollection().remove(contratopessoaCollectionNewContratopessoa);
                        oldContratopessoapessoaOfContratopessoaCollectionNewContratopessoa = em.merge(oldContratopessoapessoaOfContratopessoaCollectionNewContratopessoa);
                    }
                }
            }
            for (Juridica juridicaCollectionNewJuridica : juridicaCollectionNew) {
                if (!juridicaCollectionOld.contains(juridicaCollectionNewJuridica)) {
                    Pessoa oldJuridicapessoaOfJuridicaCollectionNewJuridica = juridicaCollectionNewJuridica.getJuridicapessoa();
                    juridicaCollectionNewJuridica.setJuridicapessoa(pessoa);
                    juridicaCollectionNewJuridica = em.merge(juridicaCollectionNewJuridica);
                    if (oldJuridicapessoaOfJuridicaCollectionNewJuridica != null && !oldJuridicapessoaOfJuridicaCollectionNewJuridica.equals(pessoa)) {
                        oldJuridicapessoaOfJuridicaCollectionNewJuridica.getJuridicaCollection().remove(juridicaCollectionNewJuridica);
                        oldJuridicapessoaOfJuridicaCollectionNewJuridica = em.merge(oldJuridicapessoaOfJuridicaCollectionNewJuridica);
                    }
                }
            }
            for (Fisica fisicaCollectionOldFisica : fisicaCollectionOld) {
                if (!fisicaCollectionNew.contains(fisicaCollectionOldFisica)) {
                    fisicaCollectionOldFisica.setFisicapessoa(null);
                    fisicaCollectionOldFisica = em.merge(fisicaCollectionOldFisica);
                }
            }
            for (Fisica fisicaCollectionNewFisica : fisicaCollectionNew) {
                if (!fisicaCollectionOld.contains(fisicaCollectionNewFisica)) {
                    Pessoa oldFisicapessoaOfFisicaCollectionNewFisica = fisicaCollectionNewFisica.getFisicapessoa();
                    fisicaCollectionNewFisica.setFisicapessoa(pessoa);
                    fisicaCollectionNewFisica = em.merge(fisicaCollectionNewFisica);
                    if (oldFisicapessoaOfFisicaCollectionNewFisica != null && !oldFisicapessoaOfFisicaCollectionNewFisica.equals(pessoa)) {
                        oldFisicapessoaOfFisicaCollectionNewFisica.getFisicaCollection().remove(fisicaCollectionNewFisica);
                        oldFisicapessoaOfFisicaCollectionNewFisica = em.merge(oldFisicapessoaOfFisicaCollectionNewFisica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pessoa.getId();
                if (findPessoa(id) == null) {
                    throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.");
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
            Pessoa pessoa;
            try {
                pessoa = em.getReference(Pessoa.class, id);
                pessoa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Advogado> advogadoCollectionOrphanCheck = pessoa.getAdvogadoCollection();
            for (Advogado advogadoCollectionOrphanCheckAdvogado : advogadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Advogado " + advogadoCollectionOrphanCheckAdvogado + " in its advogadoCollection field has a non-nullable advogadopessoa field.");
            }
            Collection<Contratopessoa> contratopessoaCollectionOrphanCheck = pessoa.getContratopessoaCollection();
            for (Contratopessoa contratopessoaCollectionOrphanCheckContratopessoa : contratopessoaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Contratopessoa " + contratopessoaCollectionOrphanCheckContratopessoa + " in its contratopessoaCollection field has a non-nullable contratopessoapessoa field.");
            }
            Collection<Juridica> juridicaCollectionOrphanCheck = pessoa.getJuridicaCollection();
            for (Juridica juridicaCollectionOrphanCheckJuridica : juridicaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pessoa (" + pessoa + ") cannot be destroyed since the Juridica " + juridicaCollectionOrphanCheckJuridica + " in its juridicaCollection field has a non-nullable juridicapessoa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rua pessoarua = pessoa.getPessoarua();
            if (pessoarua != null) {
                pessoarua.getPessoaCollection().remove(pessoa);
                pessoarua = em.merge(pessoarua);
            }
            Collection<Procuracao> procuracaoCollection = pessoa.getProcuracaoCollection();
            for (Procuracao procuracaoCollectionProcuracao : procuracaoCollection) {
                procuracaoCollectionProcuracao.setProcuracaopessoa(null);
                procuracaoCollectionProcuracao = em.merge(procuracaoCollectionProcuracao);
            }
            Collection<Telefone> telefoneCollection = pessoa.getTelefoneCollection();
            for (Telefone telefoneCollectionTelefone : telefoneCollection) {
                telefoneCollectionTelefone.setTelefonepessoa(null);
                telefoneCollectionTelefone = em.merge(telefoneCollectionTelefone);
            }
            Collection<Peticaopessoa> peticaopessoaCollection = pessoa.getPeticaopessoaCollection();
            for (Peticaopessoa peticaopessoaCollectionPeticaopessoa : peticaopessoaCollection) {
                peticaopessoaCollectionPeticaopessoa.setPeticaopessoapesssoa(null);
                peticaopessoaCollectionPeticaopessoa = em.merge(peticaopessoaCollectionPeticaopessoa);
            }
            Collection<Processopessoa> processopessoaCollection = pessoa.getProcessopessoaCollection();
            for (Processopessoa processopessoaCollectionProcessopessoa : processopessoaCollection) {
                processopessoaCollectionProcessopessoa.setProcessopessoapessoa(null);
                processopessoaCollectionProcessopessoa = em.merge(processopessoaCollectionProcessopessoa);
            }
            Collection<Contrato> contratoCollection = pessoa.getContratoCollection();
            for (Contrato contratoCollectionContrato : contratoCollection) {
                contratoCollectionContrato.setContratopessoa(null);
                contratoCollectionContrato = em.merge(contratoCollectionContrato);
            }
            Collection<Fisica> fisicaCollection = pessoa.getFisicaCollection();
            for (Fisica fisicaCollectionFisica : fisicaCollection) {
                fisicaCollectionFisica.setFisicapessoa(null);
                fisicaCollectionFisica = em.merge(fisicaCollectionFisica);
            }
            em.remove(pessoa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pessoa> findPessoaEntities() {
        return findPessoaEntities(true, -1, -1);
    }

    public List<Pessoa> findPessoaEntities(int maxResults, int firstResult) {
        return findPessoaEntities(false, maxResults, firstResult);
    }

    private List<Pessoa> findPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pessoa.class));
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

    public Pessoa findPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pessoa> rt = cq.from(Pessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

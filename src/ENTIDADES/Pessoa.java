/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTIDADES;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p")
    , @NamedQuery(name = "Pessoa.findById", query = "SELECT p FROM Pessoa p WHERE p.id = :id")
    , @NamedQuery(name = "Pessoa.findByNome", query = "SELECT p FROM Pessoa p WHERE p.nome = :nome")})
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "procuracaopessoa")
    private Collection<Procuracao> procuracaoCollection;
    @OneToMany(mappedBy = "telefonepessoa")
    private Collection<Telefone> telefoneCollection;
    @JoinColumn(name = "pessoarua", referencedColumnName = "id")
    @ManyToOne
    private Rua pessoarua;
    @OneToMany(mappedBy = "peticaopessoapesssoa")
    private Collection<Peticaopessoa> peticaopessoaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advogadopessoa")
    private Collection<Advogado> advogadoCollection;
    @OneToMany(mappedBy = "processopessoapessoa")
    private Collection<Processopessoa> processopessoaCollection;
    @OneToMany(mappedBy = "contratopessoa")
    private Collection<Contrato> contratoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratopessoapessoa")
    private Collection<Contratopessoa> contratopessoaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "juridicapessoa")
    private Collection<Juridica> juridicaCollection;
    @OneToMany(mappedBy = "fisicapessoa")
    private Collection<Fisica> fisicaCollection;

    public Pessoa() {
    }

    public Pessoa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Procuracao> getProcuracaoCollection() {
        return procuracaoCollection;
    }

    public void setProcuracaoCollection(Collection<Procuracao> procuracaoCollection) {
        this.procuracaoCollection = procuracaoCollection;
    }

    @XmlTransient
    public Collection<Telefone> getTelefoneCollection() {
        return telefoneCollection;
    }

    public void setTelefoneCollection(Collection<Telefone> telefoneCollection) {
        this.telefoneCollection = telefoneCollection;
    }

    public Rua getPessoarua() {
        return pessoarua;
    }

    public void setPessoarua(Rua pessoarua) {
        this.pessoarua = pessoarua;
    }

    @XmlTransient
    public Collection<Peticaopessoa> getPeticaopessoaCollection() {
        return peticaopessoaCollection;
    }

    public void setPeticaopessoaCollection(Collection<Peticaopessoa> peticaopessoaCollection) {
        this.peticaopessoaCollection = peticaopessoaCollection;
    }

    @XmlTransient
    public Collection<Advogado> getAdvogadoCollection() {
        return advogadoCollection;
    }

    public void setAdvogadoCollection(Collection<Advogado> advogadoCollection) {
        this.advogadoCollection = advogadoCollection;
    }

    @XmlTransient
    public Collection<Processopessoa> getProcessopessoaCollection() {
        return processopessoaCollection;
    }

    public void setProcessopessoaCollection(Collection<Processopessoa> processopessoaCollection) {
        this.processopessoaCollection = processopessoaCollection;
    }

    @XmlTransient
    public Collection<Contrato> getContratoCollection() {
        return contratoCollection;
    }

    public void setContratoCollection(Collection<Contrato> contratoCollection) {
        this.contratoCollection = contratoCollection;
    }

    @XmlTransient
    public Collection<Contratopessoa> getContratopessoaCollection() {
        return contratopessoaCollection;
    }

    public void setContratopessoaCollection(Collection<Contratopessoa> contratopessoaCollection) {
        this.contratopessoaCollection = contratopessoaCollection;
    }

    @XmlTransient
    public Collection<Juridica> getJuridicaCollection() {
        return juridicaCollection;
    }

    public void setJuridicaCollection(Collection<Juridica> juridicaCollection) {
        this.juridicaCollection = juridicaCollection;
    }

    @XmlTransient
    public Collection<Fisica> getFisicaCollection() {
        return fisicaCollection;
    }

    public void setFisicaCollection(Collection<Fisica> fisicaCollection) {
        this.fisicaCollection = fisicaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Pessoa[ id=" + id + " ]";
    }
    
}

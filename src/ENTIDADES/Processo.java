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
@Table(name = "processo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processo.findAll", query = "SELECT p FROM Processo p")
    , @NamedQuery(name = "Processo.findById", query = "SELECT p FROM Processo p WHERE p.id = :id")
    , @NamedQuery(name = "Processo.findByNumeroprocesso", query = "SELECT p FROM Processo p WHERE p.numeroprocesso = :numeroprocesso")})
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numeroprocesso")
    private int numeroprocesso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processopeticaoprocesso")
    private Collection<Processopeticao> processopeticaoCollection;
    @OneToMany(mappedBy = "processopessoaprocesso")
    private Collection<Processopessoa> processopessoaCollection;
    @OneToMany(mappedBy = "processoaudienciaprocesso")
    private Collection<Processoaudiencia> processoaudienciaCollection;
    @JoinColumn(name = "processojurisprudencia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Jurisprudencia processojurisprudencia;

    public Processo() {
    }

    public Processo(Integer id) {
        this.id = id;
    }

    public Processo(Integer id, int numeroprocesso) {
        this.id = id;
        this.numeroprocesso = numeroprocesso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroprocesso() {
        return numeroprocesso;
    }

    public void setNumeroprocesso(int numeroprocesso) {
        this.numeroprocesso = numeroprocesso;
    }

    @XmlTransient
    public Collection<Processopeticao> getProcessopeticaoCollection() {
        return processopeticaoCollection;
    }

    public void setProcessopeticaoCollection(Collection<Processopeticao> processopeticaoCollection) {
        this.processopeticaoCollection = processopeticaoCollection;
    }

    @XmlTransient
    public Collection<Processopessoa> getProcessopessoaCollection() {
        return processopessoaCollection;
    }

    public void setProcessopessoaCollection(Collection<Processopessoa> processopessoaCollection) {
        this.processopessoaCollection = processopessoaCollection;
    }

    @XmlTransient
    public Collection<Processoaudiencia> getProcessoaudienciaCollection() {
        return processoaudienciaCollection;
    }

    public void setProcessoaudienciaCollection(Collection<Processoaudiencia> processoaudienciaCollection) {
        this.processoaudienciaCollection = processoaudienciaCollection;
    }

    public Jurisprudencia getProcessojurisprudencia() {
        return processojurisprudencia;
    }

    public void setProcessojurisprudencia(Jurisprudencia processojurisprudencia) {
        this.processojurisprudencia = processojurisprudencia;
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
        if (!(object instanceof Processo)) {
            return false;
        }
        Processo other = (Processo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Processo[ id=" + id + " ]";
    }
    
}

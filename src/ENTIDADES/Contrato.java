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
@Table(name = "contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c")
    , @NamedQuery(name = "Contrato.findById", query = "SELECT c FROM Contrato c WHERE c.id = :id")
    , @NamedQuery(name = "Contrato.findByNumerocontrato", query = "SELECT c FROM Contrato c WHERE c.numerocontrato = :numerocontrato")})
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numerocontrato")
    private Integer numerocontrato;
    @JoinColumn(name = "contratopessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa contratopessoa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contratopessoacontrato")
    private Collection<Contratopessoa> contratopessoaCollection;

    public Contrato() {
    }

    public Contrato(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumerocontrato() {
        return numerocontrato;
    }

    public void setNumerocontrato(Integer numerocontrato) {
        this.numerocontrato = numerocontrato;
    }

    public Pessoa getContratopessoa() {
        return contratopessoa;
    }

    public void setContratopessoa(Pessoa contratopessoa) {
        this.contratopessoa = contratopessoa;
    }

    @XmlTransient
    public Collection<Contratopessoa> getContratopessoaCollection() {
        return contratopessoaCollection;
    }

    public void setContratopessoaCollection(Collection<Contratopessoa> contratopessoaCollection) {
        this.contratopessoaCollection = contratopessoaCollection;
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
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Contrato[ id=" + id + " ]";
    }
    
}

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
@Table(name = "cidade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c")
    , @NamedQuery(name = "Cidade.findById", query = "SELECT c FROM Cidade c WHERE c.id = :id")
    , @NamedQuery(name = "Cidade.findByNomecidade", query = "SELECT c FROM Cidade c WHERE c.nomecidade = :nomecidade")})
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nomecidade")
    private String nomecidade;
    @JoinColumn(name = "cidadeestado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado cidadeestado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bairrocidade")
    private Collection<Bairro> bairroCollection;

    public Cidade() {
    }

    public Cidade(Integer id) {
        this.id = id;
    }

    public Cidade(Integer id, String nomecidade) {
        this.id = id;
        this.nomecidade = nomecidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomecidade() {
        return nomecidade;
    }

    public void setNomecidade(String nomecidade) {
        this.nomecidade = nomecidade;
    }

    public Estado getCidadeestado() {
        return cidadeestado;
    }

    public void setCidadeestado(Estado cidadeestado) {
        this.cidadeestado = cidadeestado;
    }

    @XmlTransient
    public Collection<Bairro> getBairroCollection() {
        return bairroCollection;
    }

    public void setBairroCollection(Collection<Bairro> bairroCollection) {
        this.bairroCollection = bairroCollection;
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
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Cidade[ id=" + id + " ]";
    }
    
}

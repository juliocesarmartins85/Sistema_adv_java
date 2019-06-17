/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTIDADES;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "bairro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bairro.findAll", query = "SELECT b FROM Bairro b")
    , @NamedQuery(name = "Bairro.findById", query = "SELECT b FROM Bairro b WHERE b.id = :id")
    , @NamedQuery(name = "Bairro.findByNomebairro", query = "SELECT b FROM Bairro b WHERE b.nomebairro = :nomebairro")})
public class Bairro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nomebairro")
    private String nomebairro;
    @JoinColumn(name = "bairrocidade", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cidade bairrocidade;
    @OneToMany(mappedBy = "ruabairro")
    private Collection<Rua> ruaCollection;

    public Bairro() {
    }

    public Bairro(Integer id) {
        this.id = id;
    }

    public Bairro(Integer id, String nomebairro) {
        this.id = id;
        this.nomebairro = nomebairro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomebairro() {
        return nomebairro;
    }

    public void setNomebairro(String nomebairro) {
        this.nomebairro = nomebairro;
    }

    public Cidade getBairrocidade() {
        return bairrocidade;
    }

    public void setBairrocidade(Cidade bairrocidade) {
        this.bairrocidade = bairrocidade;
    }

    @XmlTransient
    public Collection<Rua> getRuaCollection() {
        return ruaCollection;
    }

    public void setRuaCollection(Collection<Rua> ruaCollection) {
        this.ruaCollection = ruaCollection;
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
        if (!(object instanceof Bairro)) {
            return false;
        }
        Bairro other = (Bairro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Bairro[ id=" + id + " ]";
    }
    
}

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
@Table(name = "juiz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Juiz.findAll", query = "SELECT j FROM Juiz j")
    , @NamedQuery(name = "Juiz.findById", query = "SELECT j FROM Juiz j WHERE j.id = :id")
    , @NamedQuery(name = "Juiz.findByNomejuiz", query = "SELECT j FROM Juiz j WHERE j.nomejuiz = :nomejuiz")})
public class Juiz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nomejuiz")
    private String nomejuiz;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audienciajuiz")
    private Collection<Audiencia> audienciaCollection;

    public Juiz() {
    }

    public Juiz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomejuiz() {
        return nomejuiz;
    }

    public void setNomejuiz(String nomejuiz) {
        this.nomejuiz = nomejuiz;
    }

    @XmlTransient
    public Collection<Audiencia> getAudienciaCollection() {
        return audienciaCollection;
    }

    public void setAudienciaCollection(Collection<Audiencia> audienciaCollection) {
        this.audienciaCollection = audienciaCollection;
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
        if (!(object instanceof Juiz)) {
            return false;
        }
        Juiz other = (Juiz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Juiz[ id=" + id + " ]";
    }
    
}

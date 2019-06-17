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
@Table(name = "forum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Forum.findAll", query = "SELECT f FROM Forum f")
    , @NamedQuery(name = "Forum.findById", query = "SELECT f FROM Forum f WHERE f.id = :id")
    , @NamedQuery(name = "Forum.findByTipoforum", query = "SELECT f FROM Forum f WHERE f.tipoforum = :tipoforum")
    , @NamedQuery(name = "Forum.findByNumeroautenticacao", query = "SELECT f FROM Forum f WHERE f.numeroautenticacao = :numeroautenticacao")})
public class Forum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipoforum")
    private String tipoforum;
    @Column(name = "numeroautenticacao")
    private String numeroautenticacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audienciaforum")
    private Collection<Audiencia> audienciaCollection;

    public Forum() {
    }

    public Forum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoforum() {
        return tipoforum;
    }

    public void setTipoforum(String tipoforum) {
        this.tipoforum = tipoforum;
    }

    public String getNumeroautenticacao() {
        return numeroautenticacao;
    }

    public void setNumeroautenticacao(String numeroautenticacao) {
        this.numeroautenticacao = numeroautenticacao;
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
        if (!(object instanceof Forum)) {
            return false;
        }
        Forum other = (Forum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Forum[ id=" + id + " ]";
    }
    
}

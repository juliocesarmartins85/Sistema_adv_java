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
@Table(name = "audiencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audiencia.findAll", query = "SELECT a FROM Audiencia a")
    , @NamedQuery(name = "Audiencia.findById", query = "SELECT a FROM Audiencia a WHERE a.id = :id")
    , @NamedQuery(name = "Audiencia.findByTipoaudiencia", query = "SELECT a FROM Audiencia a WHERE a.tipoaudiencia = :tipoaudiencia")})
public class Audiencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tipoaudiencia")
    private String tipoaudiencia;
    @OneToMany(mappedBy = "processoaudienciaaudiencia")
    private Collection<Processoaudiencia> processoaudienciaCollection;
    @JoinColumn(name = "audienciaforum", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Forum audienciaforum;
    @JoinColumn(name = "audienciajuiz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Juiz audienciajuiz;

    public Audiencia() {
    }

    public Audiencia(Integer id) {
        this.id = id;
    }

    public Audiencia(Integer id, String tipoaudiencia) {
        this.id = id;
        this.tipoaudiencia = tipoaudiencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoaudiencia() {
        return tipoaudiencia;
    }

    public void setTipoaudiencia(String tipoaudiencia) {
        this.tipoaudiencia = tipoaudiencia;
    }

    @XmlTransient
    public Collection<Processoaudiencia> getProcessoaudienciaCollection() {
        return processoaudienciaCollection;
    }

    public void setProcessoaudienciaCollection(Collection<Processoaudiencia> processoaudienciaCollection) {
        this.processoaudienciaCollection = processoaudienciaCollection;
    }

    public Forum getAudienciaforum() {
        return audienciaforum;
    }

    public void setAudienciaforum(Forum audienciaforum) {
        this.audienciaforum = audienciaforum;
    }

    public Juiz getAudienciajuiz() {
        return audienciajuiz;
    }

    public void setAudienciajuiz(Juiz audienciajuiz) {
        this.audienciajuiz = audienciajuiz;
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
        if (!(object instanceof Audiencia)) {
            return false;
        }
        Audiencia other = (Audiencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Audiencia[ id=" + id + " ]";
    }
    
}

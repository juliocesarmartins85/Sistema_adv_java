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
@Table(name = "jurisprudencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jurisprudencia.findAll", query = "SELECT j FROM Jurisprudencia j")
    , @NamedQuery(name = "Jurisprudencia.findById", query = "SELECT j FROM Jurisprudencia j WHERE j.id = :id")
    , @NamedQuery(name = "Jurisprudencia.findByResultado", query = "SELECT j FROM Jurisprudencia j WHERE j.resultado = :resultado")})
public class Jurisprudencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "resultado")
    private String resultado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processojurisprudencia")
    private Collection<Processo> processoCollection;

    public Jurisprudencia() {
    }

    public Jurisprudencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @XmlTransient
    public Collection<Processo> getProcessoCollection() {
        return processoCollection;
    }

    public void setProcessoCollection(Collection<Processo> processoCollection) {
        this.processoCollection = processoCollection;
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
        if (!(object instanceof Jurisprudencia)) {
            return false;
        }
        Jurisprudencia other = (Jurisprudencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Jurisprudencia[ id=" + id + " ]";
    }
    
}

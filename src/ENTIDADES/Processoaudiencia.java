/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTIDADES;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "processoaudiencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processoaudiencia.findAll", query = "SELECT p FROM Processoaudiencia p")
    , @NamedQuery(name = "Processoaudiencia.findById", query = "SELECT p FROM Processoaudiencia p WHERE p.id = :id")})
public class Processoaudiencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "processoaudienciaaudiencia", referencedColumnName = "id")
    @ManyToOne
    private Audiencia processoaudienciaaudiencia;
    @JoinColumn(name = "processoaudienciaprocesso", referencedColumnName = "id")
    @ManyToOne
    private Processo processoaudienciaprocesso;

    public Processoaudiencia() {
    }

    public Processoaudiencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Audiencia getProcessoaudienciaaudiencia() {
        return processoaudienciaaudiencia;
    }

    public void setProcessoaudienciaaudiencia(Audiencia processoaudienciaaudiencia) {
        this.processoaudienciaaudiencia = processoaudienciaaudiencia;
    }

    public Processo getProcessoaudienciaprocesso() {
        return processoaudienciaprocesso;
    }

    public void setProcessoaudienciaprocesso(Processo processoaudienciaprocesso) {
        this.processoaudienciaprocesso = processoaudienciaprocesso;
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
        if (!(object instanceof Processoaudiencia)) {
            return false;
        }
        Processoaudiencia other = (Processoaudiencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Processoaudiencia[ id=" + id + " ]";
    }
    
}

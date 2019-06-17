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
@Table(name = "procuracao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Procuracao.findAll", query = "SELECT p FROM Procuracao p")
    , @NamedQuery(name = "Procuracao.findById", query = "SELECT p FROM Procuracao p WHERE p.id = :id")})
public class Procuracao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "procuracaopessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa procuracaopessoa;

    public Procuracao() {
    }

    public Procuracao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getProcuracaopessoa() {
        return procuracaopessoa;
    }

    public void setProcuracaopessoa(Pessoa procuracaopessoa) {
        this.procuracaopessoa = procuracaopessoa;
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
        if (!(object instanceof Procuracao)) {
            return false;
        }
        Procuracao other = (Procuracao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Procuracao[ id=" + id + " ]";
    }
    
}

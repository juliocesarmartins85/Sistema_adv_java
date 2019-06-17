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
@Table(name = "processopessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processopessoa.findAll", query = "SELECT p FROM Processopessoa p")
    , @NamedQuery(name = "Processopessoa.findById", query = "SELECT p FROM Processopessoa p WHERE p.id = :id")})
public class Processopessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "processopessoapessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa processopessoapessoa;
    @JoinColumn(name = "processopessoaprocesso", referencedColumnName = "id")
    @ManyToOne
    private Processo processopessoaprocesso;

    public Processopessoa() {
    }

    public Processopessoa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getProcessopessoapessoa() {
        return processopessoapessoa;
    }

    public void setProcessopessoapessoa(Pessoa processopessoapessoa) {
        this.processopessoapessoa = processopessoapessoa;
    }

    public Processo getProcessopessoaprocesso() {
        return processopessoaprocesso;
    }

    public void setProcessopessoaprocesso(Processo processopessoaprocesso) {
        this.processopessoaprocesso = processopessoaprocesso;
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
        if (!(object instanceof Processopessoa)) {
            return false;
        }
        Processopessoa other = (Processopessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Processopessoa[ id=" + id + " ]";
    }
    
}

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
@Table(name = "advogado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advogado.findAll", query = "SELECT a FROM Advogado a")
    , @NamedQuery(name = "Advogado.findById", query = "SELECT a FROM Advogado a WHERE a.id = :id")
    , @NamedQuery(name = "Advogado.findByRegistrooab", query = "SELECT a FROM Advogado a WHERE a.registrooab = :registrooab")})
public class Advogado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "registrooab")
    private String registrooab;
    @JoinColumn(name = "advogadopessoa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pessoa advogadopessoa;

    public Advogado() {
    }

    public Advogado(Integer id) {
        this.id = id;
    }

    public Advogado(Integer id, String registrooab) {
        this.id = id;
        this.registrooab = registrooab;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrooab() {
        return registrooab;
    }

    public void setRegistrooab(String registrooab) {
        this.registrooab = registrooab;
    }

    public Pessoa getAdvogadopessoa() {
        return advogadopessoa;
    }

    public void setAdvogadopessoa(Pessoa advogadopessoa) {
        this.advogadopessoa = advogadopessoa;
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
        if (!(object instanceof Advogado)) {
            return false;
        }
        Advogado other = (Advogado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Advogado[ id=" + id + " ]";
    }
    
}

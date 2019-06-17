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
@Table(name = "juridica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Juridica.findAll", query = "SELECT j FROM Juridica j")
    , @NamedQuery(name = "Juridica.findById", query = "SELECT j FROM Juridica j WHERE j.id = :id")
    , @NamedQuery(name = "Juridica.findByCnpj", query = "SELECT j FROM Juridica j WHERE j.cnpj = :cnpj")})
public class Juridica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "cnpj")
    private int cnpj;
    @JoinColumn(name = "juridicapessoa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pessoa juridicapessoa;

    public Juridica() {
    }

    public Juridica(Integer id) {
        this.id = id;
    }

    public Juridica(Integer id, int cnpj) {
        this.id = id;
        this.cnpj = cnpj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public Pessoa getJuridicapessoa() {
        return juridicapessoa;
    }

    public void setJuridicapessoa(Pessoa juridicapessoa) {
        this.juridicapessoa = juridicapessoa;
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
        if (!(object instanceof Juridica)) {
            return false;
        }
        Juridica other = (Juridica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Juridica[ id=" + id + " ]";
    }
    
}

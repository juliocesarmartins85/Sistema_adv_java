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
@Table(name = "relatoriodejuros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relatoriodejuros.findAll", query = "SELECT r FROM Relatoriodejuros r")
    , @NamedQuery(name = "Relatoriodejuros.findById", query = "SELECT r FROM Relatoriodejuros r WHERE r.id = :id")
    , @NamedQuery(name = "Relatoriodejuros.findByAno", query = "SELECT r FROM Relatoriodejuros r WHERE r.ano = :ano")
    , @NamedQuery(name = "Relatoriodejuros.findByMes", query = "SELECT r FROM Relatoriodejuros r WHERE r.mes = :mes")
    , @NamedQuery(name = "Relatoriodejuros.findByJuros", query = "SELECT r FROM Relatoriodejuros r WHERE r.juros = :juros")
    , @NamedQuery(name = "Relatoriodejuros.findByValor", query = "SELECT r FROM Relatoriodejuros r WHERE r.valor = :valor")})
public class Relatoriodejuros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ano")
    private String ano;
    @Column(name = "mes")
    private String mes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "juros")
    private Double juros;
    @Column(name = "valor")
    private Double valor;
    @OneToMany(mappedBy = "peticaotabeladejurostabeladejuros")
    private Collection<Peticaotabeladejuros> peticaotabeladejurosCollection;

    public Relatoriodejuros() {
    }

    public Relatoriodejuros(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getJuros() {
        return juros;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @XmlTransient
    public Collection<Peticaotabeladejuros> getPeticaotabeladejurosCollection() {
        return peticaotabeladejurosCollection;
    }

    public void setPeticaotabeladejurosCollection(Collection<Peticaotabeladejuros> peticaotabeladejurosCollection) {
        this.peticaotabeladejurosCollection = peticaotabeladejurosCollection;
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
        if (!(object instanceof Relatoriodejuros)) {
            return false;
        }
        Relatoriodejuros other = (Relatoriodejuros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Relatoriodejuros[ id=" + id + " ]";
    }
    
}

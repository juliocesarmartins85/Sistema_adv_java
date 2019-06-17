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
@Table(name = "peticaotabeladejuros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peticaotabeladejuros.findAll", query = "SELECT p FROM Peticaotabeladejuros p")
    , @NamedQuery(name = "Peticaotabeladejuros.findById", query = "SELECT p FROM Peticaotabeladejuros p WHERE p.id = :id")})
public class Peticaotabeladejuros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "peticaotabeladejurospeticao", referencedColumnName = "id")
    @ManyToOne
    private Peticao peticaotabeladejurospeticao;
    @JoinColumn(name = "peticaotabeladejurostabeladejuros", referencedColumnName = "id")
    @ManyToOne
    private Relatoriodejuros peticaotabeladejurostabeladejuros;

    public Peticaotabeladejuros() {
    }

    public Peticaotabeladejuros(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Peticao getPeticaotabeladejurospeticao() {
        return peticaotabeladejurospeticao;
    }

    public void setPeticaotabeladejurospeticao(Peticao peticaotabeladejurospeticao) {
        this.peticaotabeladejurospeticao = peticaotabeladejurospeticao;
    }

    public Relatoriodejuros getPeticaotabeladejurostabeladejuros() {
        return peticaotabeladejurostabeladejuros;
    }

    public void setPeticaotabeladejurostabeladejuros(Relatoriodejuros peticaotabeladejurostabeladejuros) {
        this.peticaotabeladejurostabeladejuros = peticaotabeladejurostabeladejuros;
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
        if (!(object instanceof Peticaotabeladejuros)) {
            return false;
        }
        Peticaotabeladejuros other = (Peticaotabeladejuros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Peticaotabeladejuros[ id=" + id + " ]";
    }
    
}

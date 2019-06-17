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
@Table(name = "peticao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peticao.findAll", query = "SELECT p FROM Peticao p")
    , @NamedQuery(name = "Peticao.findById", query = "SELECT p FROM Peticao p WHERE p.id = :id")
    , @NamedQuery(name = "Peticao.findByLei", query = "SELECT p FROM Peticao p WHERE p.lei = :lei")
    , @NamedQuery(name = "Peticao.findByArtigo", query = "SELECT p FROM Peticao p WHERE p.artigo = :artigo")
    , @NamedQuery(name = "Peticao.findByParagrafo", query = "SELECT p FROM Peticao p WHERE p.paragrafo = :paragrafo")
    , @NamedQuery(name = "Peticao.findByReu", query = "SELECT p FROM Peticao p WHERE p.reu = :reu")})
public class Peticao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "lei")
    private String lei;
    @Column(name = "artigo")
    private String artigo;
    @Column(name = "paragrafo")
    private String paragrafo;
    @Column(name = "reu")
    private String reu;
    @OneToMany(mappedBy = "peticaotabeladejurospeticao")
    private Collection<Peticaotabeladejuros> peticaotabeladejurosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processopeticaopeticao")
    private Collection<Processopeticao> processopeticaoCollection;
    @OneToMany(mappedBy = "peticaopessoapeticao")
    private Collection<Peticaopessoa> peticaopessoaCollection;

    public Peticao() {
    }

    public Peticao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLei() {
        return lei;
    }

    public void setLei(String lei) {
        this.lei = lei;
    }

    public String getArtigo() {
        return artigo;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public String getParagrafo() {
        return paragrafo;
    }

    public void setParagrafo(String paragrafo) {
        this.paragrafo = paragrafo;
    }

    public String getReu() {
        return reu;
    }

    public void setReu(String reu) {
        this.reu = reu;
    }

    @XmlTransient
    public Collection<Peticaotabeladejuros> getPeticaotabeladejurosCollection() {
        return peticaotabeladejurosCollection;
    }

    public void setPeticaotabeladejurosCollection(Collection<Peticaotabeladejuros> peticaotabeladejurosCollection) {
        this.peticaotabeladejurosCollection = peticaotabeladejurosCollection;
    }

    @XmlTransient
    public Collection<Processopeticao> getProcessopeticaoCollection() {
        return processopeticaoCollection;
    }

    public void setProcessopeticaoCollection(Collection<Processopeticao> processopeticaoCollection) {
        this.processopeticaoCollection = processopeticaoCollection;
    }

    @XmlTransient
    public Collection<Peticaopessoa> getPeticaopessoaCollection() {
        return peticaopessoaCollection;
    }

    public void setPeticaopessoaCollection(Collection<Peticaopessoa> peticaopessoaCollection) {
        this.peticaopessoaCollection = peticaopessoaCollection;
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
        if (!(object instanceof Peticao)) {
            return false;
        }
        Peticao other = (Peticao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Peticao[ id=" + id + " ]";
    }
    
}

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
@Table(name = "rua")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rua.findAll", query = "SELECT r FROM Rua r")
    , @NamedQuery(name = "Rua.findById", query = "SELECT r FROM Rua r WHERE r.id = :id")
    , @NamedQuery(name = "Rua.findByNome", query = "SELECT r FROM Rua r WHERE r.nome = :nome")
    , @NamedQuery(name = "Rua.findByNumerocasa", query = "SELECT r FROM Rua r WHERE r.numerocasa = :numerocasa")
    , @NamedQuery(name = "Rua.findByCep", query = "SELECT r FROM Rua r WHERE r.cep = :cep")})
public class Rua implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "numerocasa")
    private Integer numerocasa;
    @Column(name = "cep")
    private Integer cep;
    @OneToMany(mappedBy = "pessoarua")
    private Collection<Pessoa> pessoaCollection;
    @JoinColumn(name = "ruabairro", referencedColumnName = "id")
    @ManyToOne
    private Bairro ruabairro;

    public Rua() {
    }

    public Rua(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumerocasa() {
        return numerocasa;
    }

    public void setNumerocasa(Integer numerocasa) {
        this.numerocasa = numerocasa;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    @XmlTransient
    public Collection<Pessoa> getPessoaCollection() {
        return pessoaCollection;
    }

    public void setPessoaCollection(Collection<Pessoa> pessoaCollection) {
        this.pessoaCollection = pessoaCollection;
    }

    public Bairro getRuabairro() {
        return ruabairro;
    }

    public void setRuabairro(Bairro ruabairro) {
        this.ruabairro = ruabairro;
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
        if (!(object instanceof Rua)) {
            return false;
        }
        Rua other = (Rua) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Rua[ id=" + id + " ]";
    }
    
}

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
@Table(name = "fisica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fisica.findAll", query = "SELECT f FROM Fisica f")
    , @NamedQuery(name = "Fisica.findById", query = "SELECT f FROM Fisica f WHERE f.id = :id")
    , @NamedQuery(name = "Fisica.findByNacionalidade", query = "SELECT f FROM Fisica f WHERE f.nacionalidade = :nacionalidade")
    , @NamedQuery(name = "Fisica.findByFiliacao", query = "SELECT f FROM Fisica f WHERE f.filiacao = :filiacao")
    , @NamedQuery(name = "Fisica.findByProficao", query = "SELECT f FROM Fisica f WHERE f.proficao = :proficao")
    , @NamedQuery(name = "Fisica.findByCpf", query = "SELECT f FROM Fisica f WHERE f.cpf = :cpf")
    , @NamedQuery(name = "Fisica.findByEstadocivil", query = "SELECT f FROM Fisica f WHERE f.estadocivil = :estadocivil")
    , @NamedQuery(name = "Fisica.findByNis", query = "SELECT f FROM Fisica f WHERE f.nis = :nis")})
public class Fisica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nacionalidade")
    private String nacionalidade;
    @Column(name = "filiacao")
    private String filiacao;
    @Column(name = "proficao")
    private String proficao;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "estadocivil")
    private String estadocivil;
    @Column(name = "nis")
    private String nis;
    @JoinColumn(name = "fisicapessoa", referencedColumnName = "id")
    @ManyToOne
    private Pessoa fisicapessoa;

    public Fisica() {
    }

    public Fisica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getFiliacao() {
        return filiacao;
    }

    public void setFiliacao(String filiacao) {
        this.filiacao = filiacao;
    }

    public String getProficao() {
        return proficao;
    }

    public void setProficao(String proficao) {
        this.proficao = proficao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public Pessoa getFisicapessoa() {
        return fisicapessoa;
    }

    public void setFisicapessoa(Pessoa fisicapessoa) {
        this.fisicapessoa = fisicapessoa;
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
        if (!(object instanceof Fisica)) {
            return false;
        }
        Fisica other = (Fisica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Fisica[ id=" + id + " ]";
    }
    
}

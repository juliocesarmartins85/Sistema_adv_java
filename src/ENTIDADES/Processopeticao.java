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
@Table(name = "processopeticao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Processopeticao.findAll", query = "SELECT p FROM Processopeticao p")
    , @NamedQuery(name = "Processopeticao.findById", query = "SELECT p FROM Processopeticao p WHERE p.id = :id")})
public class Processopeticao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "processopeticaopeticao", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Peticao processopeticaopeticao;
    @JoinColumn(name = "processopeticaoprocesso", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Processo processopeticaoprocesso;

    public Processopeticao() {
    }

    public Processopeticao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Peticao getProcessopeticaopeticao() {
        return processopeticaopeticao;
    }

    public void setProcessopeticaopeticao(Peticao processopeticaopeticao) {
        this.processopeticaopeticao = processopeticaopeticao;
    }

    public Processo getProcessopeticaoprocesso() {
        return processopeticaoprocesso;
    }

    public void setProcessopeticaoprocesso(Processo processopeticaoprocesso) {
        this.processopeticaoprocesso = processopeticaoprocesso;
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
        if (!(object instanceof Processopeticao)) {
            return false;
        }
        Processopeticao other = (Processopeticao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CONTROLLER.Processopeticao[ id=" + id + " ]";
    }
    
}

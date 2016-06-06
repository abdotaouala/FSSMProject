/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Mounir
 */
@Entity
@Table(name = "prixrepas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prixrepas.findAll", query = "SELECT p FROM Prixrepas p"),
    @NamedQuery(name = "Prixrepas.findByIdPrixRepas", query = "SELECT p FROM Prixrepas p WHERE p.idPrixRepas = :idPrixRepas"),
    @NamedQuery(name = "Prixrepas.findByIndiceSup", query = "SELECT p FROM Prixrepas p WHERE p.indiceSup = :indiceSup"),
    @NamedQuery(name = "Prixrepas.findByIndiceInf", query = "SELECT p FROM Prixrepas p WHERE p.indiceInf = :indiceInf"),
    @NamedQuery(name = "Prixrepas.findByPrixDs", query = "SELECT p FROM Prixrepas p WHERE p.prixDs = :prixDs"),
    @NamedQuery(name = "Prixrepas.findByPrixD", query = "SELECT p FROM Prixrepas p WHERE p.prixD = :prixD"),
    @NamedQuery(name = "Prixrepas.findByPrixRs", query = "SELECT p FROM Prixrepas p WHERE p.prixRs = :prixRs")})
public class Prixrepas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrixRepas")
    private Integer idPrixRepas;
    @Column(name = "indiceSup")
    private Integer indiceSup;
    @Column(name = "indiceInf")
    private Integer indiceInf;
    @Column(name = "prixDs")
    private Integer prixDs;
    @Column(name = "prixD")
    private Integer prixD;
    @Column(name = "prixRs")
    private Integer prixRs;
    @OneToMany(mappedBy = "idPrixRepas", fetch = FetchType.EAGER)
    private List<Indemnetedeplacementinterne> indemnetedeplacementinterneList;

    public Prixrepas() {
    }

    public Prixrepas(Integer idPrixRepas) {
        this.idPrixRepas = idPrixRepas;
    }

    public Integer getIdPrixRepas() {
        return idPrixRepas;
    }

    public void setIdPrixRepas(Integer idPrixRepas) {
        this.idPrixRepas = idPrixRepas;
    }

    public Integer getIndiceSup() {
        return indiceSup;
    }

    public void setIndiceSup(Integer indiceSup) {
        this.indiceSup = indiceSup;
    }

    public Integer getIndiceInf() {
        return indiceInf;
    }

    public void setIndiceInf(Integer indiceInf) {
        this.indiceInf = indiceInf;
    }

    public Integer getPrixDs() {
        return prixDs;
    }

    public void setPrixDs(Integer prixDs) {
        this.prixDs = prixDs;
    }

    public Integer getPrixD() {
        return prixD;
    }

    public void setPrixD(Integer prixD) {
        this.prixD = prixD;
    }

    public Integer getPrixRs() {
        return prixRs;
    }

    public void setPrixRs(Integer prixRs) {
        this.prixRs = prixRs;
    }

    @XmlTransient
    public List<Indemnetedeplacementinterne> getIndemnetedeplacementinterneList() {
        return indemnetedeplacementinterneList;
    }

    public void setIndemnetedeplacementinterneList(List<Indemnetedeplacementinterne> indemnetedeplacementinterneList) {
        this.indemnetedeplacementinterneList = indemnetedeplacementinterneList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrixRepas != null ? idPrixRepas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prixrepas)) {
            return false;
        }
        Prixrepas other = (Prixrepas) object;
        if ((this.idPrixRepas == null && other.idPrixRepas != null) || (this.idPrixRepas != null && !this.idPrixRepas.equals(other.idPrixRepas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Prixrepas[ idPrixRepas=" + idPrixRepas + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "anneebudgetaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anneebudgetaire.findAll", query = "SELECT a FROM Anneebudgetaire a"),
    @NamedQuery(name = "Anneebudgetaire.findByAnnee", query = "SELECT a FROM Anneebudgetaire a WHERE a.annee = :annee"),
    @NamedQuery(name = "Anneebudgetaire.findByMontantRap", query = "SELECT a FROM Anneebudgetaire a WHERE a.montantRap = :montantRap"),
    @NamedQuery(name = "Anneebudgetaire.findByReliquatRap", query = "SELECT a FROM Anneebudgetaire a WHERE a.reliquatRap = :reliquatRap")})
public class Anneebudgetaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "annee")
    private Integer annee;
    @Column(name = "montantRap")
    private Integer montantRap;
    @Column(name = "reliquatRap")
    private Integer reliquatRap;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anneebudgetaire", fetch = FetchType.EAGER)
    private List<Budget> budgetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annee", fetch = FetchType.EAGER)
    private List<Bordereaucomptable> bordereaucomptableList;

    public Anneebudgetaire() {
    }

    public Anneebudgetaire(Integer annee) {
        this.annee = annee;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMontantRap() {
        return montantRap;
    }

    public void setMontantRap(Integer montantRap) {
        this.montantRap = montantRap;
    }

    public Integer getReliquatRap() {
        return reliquatRap;
    }

    public void setReliquatRap(Integer reliquatRap) {
        this.reliquatRap = reliquatRap;
    }

    @XmlTransient
    public List<Budget> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<Budget> budgetList) {
        this.budgetList = budgetList;
    }

    @XmlTransient
    public List<Bordereaucomptable> getBordereaucomptableList() {
        return bordereaucomptableList;
    }

    public void setBordereaucomptableList(List<Bordereaucomptable> bordereaucomptableList) {
        this.bordereaucomptableList = bordereaucomptableList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annee != null ? annee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anneebudgetaire)) {
            return false;
        }
        Anneebudgetaire other = (Anneebudgetaire) object;
        if ((this.annee == null && other.annee != null) || (this.annee != null && !this.annee.equals(other.annee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Anneebudgetaire[ annee=" + annee + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Mounir
 */
@Entity
@Table(name = "indemntekm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indemntekm.findAll", query = "SELECT i FROM Indemntekm i"),
    @NamedQuery(name = "Indemntekm.findByIdDeplacement", query = "SELECT i FROM Indemntekm i WHERE i.indemntekmPK.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Indemntekm.findByIdIndKm", query = "SELECT i FROM Indemntekm i WHERE i.indemntekmPK.idIndKm = :idIndKm"),
    @NamedQuery(name = "Indemntekm.findByMontantDepInt", query = "SELECT i FROM Indemntekm i WHERE i.montantDepInt = :montantDepInt"),
    @NamedQuery(name = "Indemntekm.findByKmRoute", query = "SELECT i FROM Indemntekm i WHERE i.kmRoute = :kmRoute"),
    @NamedQuery(name = "Indemntekm.findByKmPiste", query = "SELECT i FROM Indemntekm i WHERE i.kmPiste = :kmPiste"),
    @NamedQuery(name = "Indemntekm.findByMntKm", query = "SELECT i FROM Indemntekm i WHERE i.mntKm = :mntKm")})
public class Indemntekm implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndemntekmPK indemntekmPK;
    @Column(name = "montantDepInt")
    private Integer montantDepInt;
    @Column(name = "kmRoute")
    private Integer kmRoute;
    @Column(name = "kmPiste")
    private Integer kmPiste;
    @Column(name = "mntKm")
    private Integer mntKm;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "indemntekm", fetch = FetchType.EAGER)
    private List<Voiture> voitureList;
    @JoinColumn(name = "idDotation", referencedColumnName = "idDotation")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dotationsecteur idDotation;
    @JoinColumn(name = "idDeplacement", referencedColumnName = "idDeplacement", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deplacement deplacement;

    public Indemntekm() {
    }

    public Indemntekm(IndemntekmPK indemntekmPK) {
        this.indemntekmPK = indemntekmPK;
    }

    public Indemntekm(int idDeplacement, int idIndKm) {
        this.indemntekmPK = new IndemntekmPK(idDeplacement, idIndKm);
    }

    public IndemntekmPK getIndemntekmPK() {
        return indemntekmPK;
    }

    public void setIndemntekmPK(IndemntekmPK indemntekmPK) {
        this.indemntekmPK = indemntekmPK;
    }

    public Integer getMontantDepInt() {
        return montantDepInt;
    }

    public void setMontantDepInt(Integer montantDepInt) {
        this.montantDepInt = montantDepInt;
    }

    public Integer getKmRoute() {
        return kmRoute;
    }

    public void setKmRoute(Integer kmRoute) {
        this.kmRoute = kmRoute;
    }

    public Integer getKmPiste() {
        return kmPiste;
    }

    public void setKmPiste(Integer kmPiste) {
        this.kmPiste = kmPiste;
    }

    public Integer getMntKm() {
        return mntKm;
    }

    public void setMntKm(Integer mntKm) {
        this.mntKm = mntKm;
    }

    @XmlTransient
    public List<Voiture> getVoitureList() {
        return voitureList;
    }

    public void setVoitureList(List<Voiture> voitureList) {
        this.voitureList = voitureList;
    }

    public Dotationsecteur getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(Dotationsecteur idDotation) {
        this.idDotation = idDotation;
    }

    public Deplacement getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indemntekmPK != null ? indemntekmPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indemntekm)) {
            return false;
        }
        Indemntekm other = (Indemntekm) object;
        if ((this.indemntekmPK == null && other.indemntekmPK != null) || (this.indemntekmPK != null && !this.indemntekmPK.equals(other.indemntekmPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Indemntekm[ indemntekmPK=" + indemntekmPK + " ]";
    }
    
}

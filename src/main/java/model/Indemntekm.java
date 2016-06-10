/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "indemntekm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indemntekm.findAll", query = "SELECT i FROM Indemntekm i"),
    @NamedQuery(name = "Indemntekm.findByIdDeplacement", query = "SELECT i FROM Indemntekm i WHERE i.indemntekmPK.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Indemntekm.findByIdIndKm", query = "SELECT i FROM Indemntekm i WHERE i.indemntekmPK.idIndKm = :idIndKm"),
    @NamedQuery(name = "Indemntekm.findByIdDotation", query = "SELECT i FROM Indemntekm i WHERE i.idDotation = :idDotation"),
    @NamedQuery(name = "Indemntekm.findByMontantDepInt", query = "SELECT i FROM Indemntekm i WHERE i.montantDepInt = :montantDepInt"),
    @NamedQuery(name = "Indemntekm.findByKmRoute", query = "SELECT i FROM Indemntekm i WHERE i.kmRoute = :kmRoute"),
    @NamedQuery(name = "Indemntekm.findByKmPiste", query = "SELECT i FROM Indemntekm i WHERE i.kmPiste = :kmPiste"),
    @NamedQuery(name = "Indemntekm.findByMntKm", query = "SELECT i FROM Indemntekm i WHERE i.mntKm = :mntKm")})
public class Indemntekm implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndemntekmPK indemntekmPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDotation")
    private int idDotation;
    @Column(name = "montantDepInt")
    private Integer montantDepInt;
    @Column(name = "kmRoute")
    private Integer kmRoute;
    @Column(name = "kmPiste")
    private Integer kmPiste;
    @Column(name = "mntKm")
    private Integer mntKm;

    public Indemntekm() {
    }

    public Indemntekm(IndemntekmPK indemntekmPK) {
        this.indemntekmPK = indemntekmPK;
    }

    public Indemntekm(IndemntekmPK indemntekmPK, int idDotation) {
        this.indemntekmPK = indemntekmPK;
        this.idDotation = idDotation;
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

    public int getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(int idDotation) {
        this.idDotation = idDotation;
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

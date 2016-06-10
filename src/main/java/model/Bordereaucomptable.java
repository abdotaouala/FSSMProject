/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "bordereaucomptable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bordereaucomptable.findAll", query = "SELECT b FROM Bordereaucomptable b"),
    @NamedQuery(name = "Bordereaucomptable.findByIdBordComp", query = "SELECT b FROM Bordereaucomptable b WHERE b.idBordComp = :idBordComp"),
    @NamedQuery(name = "Bordereaucomptable.findByAnnee", query = "SELECT b FROM Bordereaucomptable b WHERE b.annee = :annee"),
    @NamedQuery(name = "Bordereaucomptable.findByDateExercice", query = "SELECT b FROM Bordereaucomptable b WHERE b.dateExercice = :dateExercice"),
    @NamedQuery(name = "Bordereaucomptable.findByTotalIr", query = "SELECT b FROM Bordereaucomptable b WHERE b.totalIr = :totalIr"),
    @NamedQuery(name = "Bordereaucomptable.findByTotalNet", query = "SELECT b FROM Bordereaucomptable b WHERE b.totalNet = :totalNet")})
public class Bordereaucomptable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBordComp")
    private Integer idBordComp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "annee")
    private int annee;
    @Column(name = "dateExercice")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExercice;
    @Column(name = "totalIr")
    private Integer totalIr;
    @Column(name = "totalNet")
    private Integer totalNet;

    public Bordereaucomptable() {
    }

    public Bordereaucomptable(Integer idBordComp) {
        this.idBordComp = idBordComp;
    }

    public Bordereaucomptable(Integer idBordComp, int annee) {
        this.idBordComp = idBordComp;
        this.annee = annee;
    }

    public Integer getIdBordComp() {
        return idBordComp;
    }

    public void setIdBordComp(Integer idBordComp) {
        this.idBordComp = idBordComp;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Date getDateExercice() {
        return dateExercice;
    }

    public void setDateExercice(Date dateExercice) {
        this.dateExercice = dateExercice;
    }

    public Integer getTotalIr() {
        return totalIr;
    }

    public void setTotalIr(Integer totalIr) {
        this.totalIr = totalIr;
    }

    public Integer getTotalNet() {
        return totalNet;
    }

    public void setTotalNet(Integer totalNet) {
        this.totalNet = totalNet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBordComp != null ? idBordComp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bordereaucomptable)) {
            return false;
        }
        Bordereaucomptable other = (Bordereaucomptable) object;
        if ((this.idBordComp == null && other.idBordComp != null) || (this.idBordComp != null && !this.idBordComp.equals(other.idBordComp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Bordereaucomptable[ idBordComp=" + idBordComp + " ]";
    }
    
}

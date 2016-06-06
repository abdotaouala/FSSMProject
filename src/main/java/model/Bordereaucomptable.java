/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "bordereaucomptable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bordereaucomptable.findAll", query = "SELECT b FROM Bordereaucomptable b"),
    @NamedQuery(name = "Bordereaucomptable.findByIdBordComp", query = "SELECT b FROM Bordereaucomptable b WHERE b.idBordComp = :idBordComp"),
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
    @Column(name = "dateExercice")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExercice;
    @Column(name = "totalIr")
    private Integer totalIr;
    @Column(name = "totalNet")
    private Integer totalNet;
    @OneToMany(mappedBy = "idBordComp", fetch = FetchType.EAGER)
    private List<Dossiervacataire> dossiervacataireList;
    @OneToMany(mappedBy = "idBordComp", fetch = FetchType.EAGER)
    private List<Dossierhsupp> dossierhsuppList;
    @JoinColumn(name = "annee", referencedColumnName = "annee")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Anneebudgetaire annee;

    public Bordereaucomptable() {
    }

    public Bordereaucomptable(Integer idBordComp) {
        this.idBordComp = idBordComp;
    }

    public Integer getIdBordComp() {
        return idBordComp;
    }

    public void setIdBordComp(Integer idBordComp) {
        this.idBordComp = idBordComp;
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

    @XmlTransient
    public List<Dossiervacataire> getDossiervacataireList() {
        return dossiervacataireList;
    }

    public void setDossiervacataireList(List<Dossiervacataire> dossiervacataireList) {
        this.dossiervacataireList = dossiervacataireList;
    }

    @XmlTransient
    public List<Dossierhsupp> getDossierhsuppList() {
        return dossierhsuppList;
    }

    public void setDossierhsuppList(List<Dossierhsupp> dossierhsuppList) {
        this.dossierhsuppList = dossierhsuppList;
    }

    public Anneebudgetaire getAnnee() {
        return annee;
    }

    public void setAnnee(Anneebudgetaire annee) {
        this.annee = annee;
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

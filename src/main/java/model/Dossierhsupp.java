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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "dossierhsupp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dossierhsupp.findAll", query = "SELECT d FROM Dossierhsupp d"),
    @NamedQuery(name = "Dossierhsupp.findByIdDossier", query = "SELECT d FROM Dossierhsupp d WHERE d.idDossier = :idDossier"),
    @NamedQuery(name = "Dossierhsupp.findByCinPpr", query = "SELECT d FROM Dossierhsupp d WHERE d.cinPpr = :cinPpr"),
    @NamedQuery(name = "Dossierhsupp.findByIdBordAut", query = "SELECT d FROM Dossierhsupp d WHERE d.idBordAut = :idBordAut"),
    @NamedQuery(name = "Dossierhsupp.findByIdDossierProv", query = "SELECT d FROM Dossierhsupp d WHERE d.idDossierProv = :idDossierProv"),
    @NamedQuery(name = "Dossierhsupp.findByIdBordComp", query = "SELECT d FROM Dossierhsupp d WHERE d.idBordComp = :idBordComp"),
    @NamedQuery(name = "Dossierhsupp.findByIdDotation", query = "SELECT d FROM Dossierhsupp d WHERE d.idDotation = :idDotation"),
    @NamedQuery(name = "Dossierhsupp.findByIdGrade", query = "SELECT d FROM Dossierhsupp d WHERE d.idGrade = :idGrade"),
    @NamedQuery(name = "Dossierhsupp.findByIdDetail", query = "SELECT d FROM Dossierhsupp d WHERE d.idDetail = :idDetail"),
    @NamedQuery(name = "Dossierhsupp.findByNbrHeures", query = "SELECT d FROM Dossierhsupp d WHERE d.nbrHeures = :nbrHeures"),
    @NamedQuery(name = "Dossierhsupp.findByMois", query = "SELECT d FROM Dossierhsupp d WHERE d.mois = :mois"),
    @NamedQuery(name = "Dossierhsupp.findBySemestre", query = "SELECT d FROM Dossierhsupp d WHERE d.semestre = :semestre"),
    @NamedQuery(name = "Dossierhsupp.findByDateCreance", query = "SELECT d FROM Dossierhsupp d WHERE d.dateCreance = :dateCreance"),
    @NamedQuery(name = "Dossierhsupp.findByMontantHsupp", query = "SELECT d FROM Dossierhsupp d WHERE d.montantHsupp = :montantHsupp"),
    @NamedQuery(name = "Dossierhsupp.findByStatutDossier", query = "SELECT d FROM Dossierhsupp d WHERE d.statutDossier = :statutDossier")})
public class Dossierhsupp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDossier")
    private Integer idDossier;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "cinPpr")
    private String cinPpr;
    @Column(name = "idBordAut")
    private Integer idBordAut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDossierProv")
    private int idDossierProv;
    @Column(name = "idBordComp")
    private Integer idBordComp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDotation")
    private int idDotation;
    @Column(name = "idGrade")
    private Integer idGrade;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDetail")
    private int idDetail;
    @Column(name = "nbrHeures")
    private Integer nbrHeures;
    @Size(max = 254)
    @Column(name = "mois")
    private String mois;
    @Size(max = 254)
    @Column(name = "semestre")
    private String semestre;
    @Column(name = "dateCreance")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreance;
    @Column(name = "montantHsupp")
    private Integer montantHsupp;
    @Size(max = 254)
    @Column(name = "statutDossier")
    private String statutDossier;

    public Dossierhsupp() {
    }

    public Dossierhsupp(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Dossierhsupp(Integer idDossier, String cinPpr, int idDossierProv, int idDotation, int idDetail) {
        this.idDossier = idDossier;
        this.cinPpr = cinPpr;
        this.idDossierProv = idDossierProv;
        this.idDotation = idDotation;
        this.idDetail = idDetail;
    }

    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public String getCinPpr() {
        return cinPpr;
    }

    public void setCinPpr(String cinPpr) {
        this.cinPpr = cinPpr;
    }

    public Integer getIdBordAut() {
        return idBordAut;
    }

    public void setIdBordAut(Integer idBordAut) {
        this.idBordAut = idBordAut;
    }

    public int getIdDossierProv() {
        return idDossierProv;
    }

    public void setIdDossierProv(int idDossierProv) {
        this.idDossierProv = idDossierProv;
    }

    public Integer getIdBordComp() {
        return idBordComp;
    }

    public void setIdBordComp(Integer idBordComp) {
        this.idBordComp = idBordComp;
    }

    public int getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(int idDotation) {
        this.idDotation = idDotation;
    }

    public Integer getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(Integer idGrade) {
        this.idGrade = idGrade;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public Integer getNbrHeures() {
        return nbrHeures;
    }

    public void setNbrHeures(Integer nbrHeures) {
        this.nbrHeures = nbrHeures;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Date getDateCreance() {
        return dateCreance;
    }

    public void setDateCreance(Date dateCreance) {
        this.dateCreance = dateCreance;
    }

    public Integer getMontantHsupp() {
        return montantHsupp;
    }

    public void setMontantHsupp(Integer montantHsupp) {
        this.montantHsupp = montantHsupp;
    }

    public String getStatutDossier() {
        return statutDossier;
    }

    public void setStatutDossier(String statutDossier) {
        this.statutDossier = statutDossier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDossier != null ? idDossier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dossierhsupp)) {
            return false;
        }
        Dossierhsupp other = (Dossierhsupp) object;
        if ((this.idDossier == null && other.idDossier != null) || (this.idDossier != null && !this.idDossier.equals(other.idDossier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Dossierhsupp[ idDossier=" + idDossier + " ]";
    }
    
}

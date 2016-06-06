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
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "dossierhsupp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dossierhsupp.findAll", query = "SELECT d FROM Dossierhsupp d"),
    @NamedQuery(name = "Dossierhsupp.findByIdDossier", query = "SELECT d FROM Dossierhsupp d WHERE d.idDossier = :idDossier"),
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dosidDossier", fetch = FetchType.EAGER)
    private List<Dossierrejete> dossierrejeteList;
    @JoinColumn(name = "idDotation", referencedColumnName = "idDotation")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dotationsecteur idDotation;
    @JoinColumn(name = "idGrade", referencedColumnName = "idGrade")
    @ManyToOne(fetch = FetchType.EAGER)
    private Graddiplome idGrade;
    @JoinColumn(name = "idDetail", referencedColumnName = "idDetail")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Detail idDetail;
    @JoinColumn(name = "idDossierProv", referencedColumnName = "idDossierProv")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dossierprovisoir idDossierProv;
    @JoinColumn(name = "idBordAut", referencedColumnName = "idBordAut")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bordereauautorisation idBordAut;
    @JoinColumn(name = "idBordComp", referencedColumnName = "idBordComp")
    @ManyToOne(fetch = FetchType.EAGER)
    private Bordereaucomptable idBordComp;
    @JoinColumn(name = "cinPpr", referencedColumnName = "cinPpr")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Intervenant cinPpr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dosidDossier", fetch = FetchType.EAGER)
    private List<Piecejustificativevacation> piecejustificativevacationList;

    public Dossierhsupp() {
    }

    public Dossierhsupp(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
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

    @XmlTransient
    public List<Dossierrejete> getDossierrejeteList() {
        return dossierrejeteList;
    }

    public void setDossierrejeteList(List<Dossierrejete> dossierrejeteList) {
        this.dossierrejeteList = dossierrejeteList;
    }

    public Dotationsecteur getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(Dotationsecteur idDotation) {
        this.idDotation = idDotation;
    }

    public Graddiplome getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(Graddiplome idGrade) {
        this.idGrade = idGrade;
    }

    public Detail getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(Detail idDetail) {
        this.idDetail = idDetail;
    }

    public Dossierprovisoir getIdDossierProv() {
        return idDossierProv;
    }

    public void setIdDossierProv(Dossierprovisoir idDossierProv) {
        this.idDossierProv = idDossierProv;
    }

    public Bordereauautorisation getIdBordAut() {
        return idBordAut;
    }

    public void setIdBordAut(Bordereauautorisation idBordAut) {
        this.idBordAut = idBordAut;
    }

    public Bordereaucomptable getIdBordComp() {
        return idBordComp;
    }

    public void setIdBordComp(Bordereaucomptable idBordComp) {
        this.idBordComp = idBordComp;
    }

    public Intervenant getCinPpr() {
        return cinPpr;
    }

    public void setCinPpr(Intervenant cinPpr) {
        this.cinPpr = cinPpr;
    }

    @XmlTransient
    public List<Piecejustificativevacation> getPiecejustificativevacationList() {
        return piecejustificativevacationList;
    }

    public void setPiecejustificativevacationList(List<Piecejustificativevacation> piecejustificativevacationList) {
        this.piecejustificativevacationList = piecejustificativevacationList;
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

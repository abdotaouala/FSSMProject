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
@Table(name = "dossiervacataire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dossiervacataire.findAll", query = "SELECT d FROM Dossiervacataire d"),
    @NamedQuery(name = "Dossiervacataire.findByIdDossier", query = "SELECT d FROM Dossiervacataire d WHERE d.idDossier = :idDossier"),
    @NamedQuery(name = "Dossiervacataire.findByCinPpr", query = "SELECT d FROM Dossiervacataire d WHERE d.cinPpr = :cinPpr"),
    @NamedQuery(name = "Dossiervacataire.findByIdBordAut", query = "SELECT d FROM Dossiervacataire d WHERE d.idBordAut = :idBordAut"),
    @NamedQuery(name = "Dossiervacataire.findByIdDossierProv", query = "SELECT d FROM Dossiervacataire d WHERE d.idDossierProv = :idDossierProv"),
    @NamedQuery(name = "Dossiervacataire.findByIdBordComp", query = "SELECT d FROM Dossiervacataire d WHERE d.idBordComp = :idBordComp"),
    @NamedQuery(name = "Dossiervacataire.findByIdDotation", query = "SELECT d FROM Dossiervacataire d WHERE d.idDotation = :idDotation"),
    @NamedQuery(name = "Dossiervacataire.findByIdGrade", query = "SELECT d FROM Dossiervacataire d WHERE d.idGrade = :idGrade"),
    @NamedQuery(name = "Dossiervacataire.findByNbrHeures", query = "SELECT d FROM Dossiervacataire d WHERE d.nbrHeures = :nbrHeures"),
    @NamedQuery(name = "Dossiervacataire.findByMois", query = "SELECT d FROM Dossiervacataire d WHERE d.mois = :mois"),
    @NamedQuery(name = "Dossiervacataire.findBySemestre", query = "SELECT d FROM Dossiervacataire d WHERE d.semestre = :semestre"),
    @NamedQuery(name = "Dossiervacataire.findByDateCreance", query = "SELECT d FROM Dossiervacataire d WHERE d.dateCreance = :dateCreance"),
    @NamedQuery(name = "Dossiervacataire.findByNet", query = "SELECT d FROM Dossiervacataire d WHERE d.net = :net"),
    @NamedQuery(name = "Dossiervacataire.findByIr", query = "SELECT d FROM Dossiervacataire d WHERE d.ir = :ir"),
    @NamedQuery(name = "Dossiervacataire.findByStatutDossier", query = "SELECT d FROM Dossiervacataire d WHERE d.statutDossier = :statutDossier")})
public class Dossiervacataire implements Serializable {

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
    @Column(name = "net")
    private Integer net;
    @Column(name = "ir")
    private Integer ir;
    @Size(max = 254)
    @Column(name = "statutDossier")
    private String statutDossier;

    public Dossiervacataire() {
    }

    public Dossiervacataire(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Dossiervacataire(Integer idDossier, String cinPpr, int idDossierProv, int idDotation) {
        this.idDossier = idDossier;
        this.cinPpr = cinPpr;
        this.idDossierProv = idDossierProv;
        this.idDotation = idDotation;
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

    public Integer getNet() {
        return net;
    }

    public void setNet(Integer net) {
        this.net = net;
    }

    public Integer getIr() {
        return ir;
    }

    public void setIr(Integer ir) {
        this.ir = ir;
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
        if (!(object instanceof Dossiervacataire)) {
            return false;
        }
        Dossiervacataire other = (Dossiervacataire) object;
        if ((this.idDossier == null && other.idDossier != null) || (this.idDossier != null && !this.idDossier.equals(other.idDossier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Dossiervacataire[ idDossier=" + idDossier + " ]";
    }
    
}

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
@Table(name = "deplacement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deplacement.findAll", query = "SELECT d FROM Deplacement d"),
    @NamedQuery(name = "Deplacement.findByIdDeplacement", query = "SELECT d FROM Deplacement d WHERE d.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Deplacement.findByCinPpr", query = "SELECT d FROM Deplacement d WHERE d.cinPpr = :cinPpr"),
    @NamedQuery(name = "Deplacement.findByIdUser", query = "SELECT d FROM Deplacement d WHERE d.idUser = :idUser"),
    @NamedQuery(name = "Deplacement.findByIdPays", query = "SELECT d FROM Deplacement d WHERE d.idPays = :idPays"),
    @NamedQuery(name = "Deplacement.findByNbrJours", query = "SELECT d FROM Deplacement d WHERE d.nbrJours = :nbrJours"),
    @NamedQuery(name = "Deplacement.findByDateDepart", query = "SELECT d FROM Deplacement d WHERE d.dateDepart = :dateDepart"),
    @NamedQuery(name = "Deplacement.findByDateArrive", query = "SELECT d FROM Deplacement d WHERE d.dateArrive = :dateArrive"),
    @NamedQuery(name = "Deplacement.findByAnnee", query = "SELECT d FROM Deplacement d WHERE d.annee = :annee"),
    @NamedQuery(name = "Deplacement.findByMotifDeplacement", query = "SELECT d FROM Deplacement d WHERE d.motifDeplacement = :motifDeplacement"),
    @NamedQuery(name = "Deplacement.findByDateCreation", query = "SELECT d FROM Deplacement d WHERE d.dateCreation = :dateCreation"),
    @NamedQuery(name = "Deplacement.findByStatutMnt", query = "SELECT d FROM Deplacement d WHERE d.statutMnt = :statutMnt"),
    @NamedQuery(name = "Deplacement.findByIndice", query = "SELECT d FROM Deplacement d WHERE d.indice = :indice"),
    @NamedQuery(name = "Deplacement.findByEchelle", query = "SELECT d FROM Deplacement d WHERE d.echelle = :echelle"),
    @NamedQuery(name = "Deplacement.findByGrade", query = "SELECT d FROM Deplacement d WHERE d.grade = :grade")})
public class Deplacement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDeplacement")
    private Integer idDeplacement;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "cinPpr")
    private String cinPpr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUser")
    private int idUser;
    @Column(name = "idPays")
    private Integer idPays;
    @Column(name = "nbrJours")
    private Integer nbrJours;
    @Column(name = "dateDepart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;
    @Column(name = "dateArrive")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateArrive;
    @Column(name = "annee")
    private Integer annee;
    @Size(max = 254)
    @Column(name = "motifDeplacement")
    private String motifDeplacement;
    @Column(name = "dateCreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Column(name = "statutMnt")
    private Integer statutMnt;
    @Column(name = "indice")
    private Integer indice;
    @Column(name = "echelle")
    private Integer echelle;
    @Size(max = 254)
    @Column(name = "grade")
    private String grade;

    public Deplacement() {
    }

    public Deplacement(Integer idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public Deplacement(Integer idDeplacement, String cinPpr, int idUser) {
        this.idDeplacement = idDeplacement;
        this.cinPpr = cinPpr;
        this.idUser = idUser;
    }

    public Integer getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(Integer idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public String getCinPpr() {
        return cinPpr;
    }

    public void setCinPpr(String cinPpr) {
        this.cinPpr = cinPpr;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public Integer getNbrJours() {
        return nbrJours;
    }

    public void setNbrJours(Integer nbrJours) {
        this.nbrJours = nbrJours;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public String getMotifDeplacement() {
        return motifDeplacement;
    }

    public void setMotifDeplacement(String motifDeplacement) {
        this.motifDeplacement = motifDeplacement;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Integer getStatutMnt() {
        return statutMnt;
    }

    public void setStatutMnt(Integer statutMnt) {
        this.statutMnt = statutMnt;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public Integer getEchelle() {
        return echelle;
    }

    public void setEchelle(Integer echelle) {
        this.echelle = echelle;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDeplacement != null ? idDeplacement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deplacement)) {
            return false;
        }
        Deplacement other = (Deplacement) object;
        if ((this.idDeplacement == null && other.idDeplacement != null) || (this.idDeplacement != null && !this.idDeplacement.equals(other.idDeplacement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Deplacement[ idDeplacement=" + idDeplacement + " ]";
    }
    
}

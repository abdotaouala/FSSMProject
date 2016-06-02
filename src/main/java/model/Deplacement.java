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
@Table(name = "deplacement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deplacement.findAll", query = "SELECT d FROM Deplacement d"),
    @NamedQuery(name = "Deplacement.findByIdDeplacement", query = "SELECT d FROM Deplacement d WHERE d.idDeplacement = :idDeplacement"),
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deplacement", fetch = FetchType.EAGER)
    private List<Indemntekm> indemntekmList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deplacement", fetch = FetchType.EAGER)
    private List<Indemnetedeplacementetranger> indemnetedeplacementetrangerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDeplacement", fetch = FetchType.EAGER)
    private List<Piecejustificativedeplacement> piecejustificativedeplacementList;
    @JoinColumn(name = "idPays", referencedColumnName = "idPays")
    @ManyToOne(fetch = FetchType.EAGER)
    private Pays idPays;
    @JoinColumn(name = "cinPpr", referencedColumnName = "cinPpr")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Intervenant cinPpr;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Users idUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDeplacement", fetch = FetchType.EAGER)
    private List<Etatdossier> etatdossierList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deplacement", fetch = FetchType.EAGER)
    private List<Indemnetedeplacementinterne> indemnetedeplacementinterneList;

    public Deplacement() {
    }

    public Deplacement(Integer idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public Integer getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(Integer idDeplacement) {
        this.idDeplacement = idDeplacement;
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

    @XmlTransient
    public List<Indemntekm> getIndemntekmList() {
        return indemntekmList;
    }

    public void setIndemntekmList(List<Indemntekm> indemntekmList) {
        this.indemntekmList = indemntekmList;
    }

    @XmlTransient
    public List<Indemnetedeplacementetranger> getIndemnetedeplacementetrangerList() {
        return indemnetedeplacementetrangerList;
    }

    public void setIndemnetedeplacementetrangerList(List<Indemnetedeplacementetranger> indemnetedeplacementetrangerList) {
        this.indemnetedeplacementetrangerList = indemnetedeplacementetrangerList;
    }

    @XmlTransient
    public List<Piecejustificativedeplacement> getPiecejustificativedeplacementList() {
        return piecejustificativedeplacementList;
    }

    public void setPiecejustificativedeplacementList(List<Piecejustificativedeplacement> piecejustificativedeplacementList) {
        this.piecejustificativedeplacementList = piecejustificativedeplacementList;
    }

    public Pays getIdPays() {
        return idPays;
    }

    public void setIdPays(Pays idPays) {
        this.idPays = idPays;
    }

    public Intervenant getCinPpr() {
        return cinPpr;
    }

    public void setCinPpr(Intervenant cinPpr) {
        this.cinPpr = cinPpr;
    }

    public Users getIdUser() {
        return idUser;
    }

    public void setIdUser(Users idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public List<Etatdossier> getEtatdossierList() {
        return etatdossierList;
    }

    public void setEtatdossierList(List<Etatdossier> etatdossierList) {
        this.etatdossierList = etatdossierList;
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

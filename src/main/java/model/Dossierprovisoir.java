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
@Table(name = "dossierprovisoir")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dossierprovisoir.findAll", query = "SELECT d FROM Dossierprovisoir d"),
    @NamedQuery(name = "Dossierprovisoir.findByIdDossierProv", query = "SELECT d FROM Dossierprovisoir d WHERE d.idDossierProv = :idDossierProv"),
    @NamedQuery(name = "Dossierprovisoir.findByNomComplet", query = "SELECT d FROM Dossierprovisoir d WHERE d.nomComplet = :nomComplet"),
    @NamedQuery(name = "Dossierprovisoir.findByDernierDiplome", query = "SELECT d FROM Dossierprovisoir d WHERE d.dernierDiplome = :dernierDiplome"),
    @NamedQuery(name = "Dossierprovisoir.findByNbrHeures", query = "SELECT d FROM Dossierprovisoir d WHERE d.nbrHeures = :nbrHeures"),
    @NamedQuery(name = "Dossierprovisoir.findByModule", query = "SELECT d FROM Dossierprovisoir d WHERE d.module = :module"),
    @NamedQuery(name = "Dossierprovisoir.findByEtat", query = "SELECT d FROM Dossierprovisoir d WHERE d.etat = :etat"),
    @NamedQuery(name = "Dossierprovisoir.findByDateDebut", query = "SELECT d FROM Dossierprovisoir d WHERE d.dateDebut = :dateDebut"),
    @NamedQuery(name = "Dossierprovisoir.findByDateFin", query = "SELECT d FROM Dossierprovisoir d WHERE d.dateFin = :dateFin")})
public class Dossierprovisoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDossierProv")
    private Integer idDossierProv;
    @Size(max = 254)
    @Column(name = "nomComplet")
    private String nomComplet;
    @Size(max = 254)
    @Column(name = "dernierDiplome")
    private String dernierDiplome;
    @Column(name = "nbrHeures")
    private Integer nbrHeures;
    @Size(max = 254)
    @Column(name = "module")
    private String module;
    @Column(name = "etat")
    private Boolean etat;
    @Column(name = "dateDebut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @Column(name = "dateFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDossierProv", fetch = FetchType.EAGER)
    private List<Dossiervacataire> dossiervacataireList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDossierProv", fetch = FetchType.EAGER)
    private List<Dossierhsupp> dossierhsuppList;
    @JoinColumn(name = "idGrade", referencedColumnName = "idGrade")
    @ManyToOne(fetch = FetchType.EAGER)
    private Graddiplome idGrade;
    @JoinColumn(name = "idRelever", referencedColumnName = "idRelever")
    @ManyToOne(fetch = FetchType.EAGER)
    private Releve idRelever;

    public Dossierprovisoir() {
    }

    public Dossierprovisoir(Integer idDossierProv) {
        this.idDossierProv = idDossierProv;
    }

    public Integer getIdDossierProv() {
        return idDossierProv;
    }

    public void setIdDossierProv(Integer idDossierProv) {
        this.idDossierProv = idDossierProv;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getDernierDiplome() {
        return dernierDiplome;
    }

    public void setDernierDiplome(String dernierDiplome) {
        this.dernierDiplome = dernierDiplome;
    }

    public Integer getNbrHeures() {
        return nbrHeures;
    }

    public void setNbrHeures(Integer nbrHeures) {
        this.nbrHeures = nbrHeures;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
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

    public Graddiplome getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(Graddiplome idGrade) {
        this.idGrade = idGrade;
    }

    public Releve getIdRelever() {
        return idRelever;
    }

    public void setIdRelever(Releve idRelever) {
        this.idRelever = idRelever;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDossierProv != null ? idDossierProv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dossierprovisoir)) {
            return false;
        }
        Dossierprovisoir other = (Dossierprovisoir) object;
        if ((this.idDossierProv == null && other.idDossierProv != null) || (this.idDossierProv != null && !this.idDossierProv.equals(other.idDossierProv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Dossierprovisoir[ idDossierProv=" + idDossierProv + " ]";
    }
    
}

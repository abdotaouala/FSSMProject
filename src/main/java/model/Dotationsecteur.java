/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "dotationsecteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dotationsecteur.findAll", query = "SELECT d FROM Dotationsecteur d"),
    @NamedQuery(name = "Dotationsecteur.findByIdDotation", query = "SELECT d FROM Dotationsecteur d WHERE d.idDotation = :idDotation"),
    @NamedQuery(name = "Dotationsecteur.findByMontantInitial", query = "SELECT d FROM Dotationsecteur d WHERE d.montantInitial = :montantInitial"),
    @NamedQuery(name = "Dotationsecteur.findByReliquat", query = "SELECT d FROM Dotationsecteur d WHERE d.reliquat = :reliquat")})
public class Dotationsecteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDotation")
    private Integer idDotation;
    @Column(name = "montantInitial")
    private Integer montantInitial;
    @Column(name = "reliquat")
    private Integer reliquat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDotation", fetch = FetchType.EAGER)
    private List<Indemntekm> indemntekmList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDotation", fetch = FetchType.EAGER)
    private List<Dossiervacataire> dossiervacataireList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDotation", fetch = FetchType.EAGER)
    private List<Boncommande> boncommandeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDotation", fetch = FetchType.EAGER)
    private List<Indemnetedeplacementetranger> indemnetedeplacementetrangerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDotation", fetch = FetchType.EAGER)
    private List<Dossierhsupp> dossierhsuppList;
    @JoinColumn(name = "idCompte", referencedColumnName = "idCompte")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Compte idCompte;
    @JoinColumn(name = "idSecteur", referencedColumnName = "idSecteur")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Secteur idSecteur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDotation", fetch = FetchType.EAGER)
    private List<Indemnetedeplacementinterne> indemnetedeplacementinterneList;

    public Dotationsecteur() {
    }

    public Dotationsecteur(Integer idDotation) {
        this.idDotation = idDotation;
    }

    public Integer getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(Integer idDotation) {
        this.idDotation = idDotation;
    }

    public Integer getMontantInitial() {
        return montantInitial;
    }

    public void setMontantInitial(Integer montantInitial) {
        this.montantInitial = montantInitial;
    }

    public Integer getReliquat() {
        return reliquat;
    }

    public void setReliquat(Integer reliquat) {
        this.reliquat = reliquat;
    }

    @XmlTransient
    public List<Indemntekm> getIndemntekmList() {
        return indemntekmList;
    }

    public void setIndemntekmList(List<Indemntekm> indemntekmList) {
        this.indemntekmList = indemntekmList;
    }

    @XmlTransient
    public List<Dossiervacataire> getDossiervacataireList() {
        return dossiervacataireList;
    }

    public void setDossiervacataireList(List<Dossiervacataire> dossiervacataireList) {
        this.dossiervacataireList = dossiervacataireList;
    }

    @XmlTransient
    public List<Boncommande> getBoncommandeList() {
        return boncommandeList;
    }

    public void setBoncommandeList(List<Boncommande> boncommandeList) {
        this.boncommandeList = boncommandeList;
    }

    @XmlTransient
    public List<Indemnetedeplacementetranger> getIndemnetedeplacementetrangerList() {
        return indemnetedeplacementetrangerList;
    }

    public void setIndemnetedeplacementetrangerList(List<Indemnetedeplacementetranger> indemnetedeplacementetrangerList) {
        this.indemnetedeplacementetrangerList = indemnetedeplacementetrangerList;
    }

    @XmlTransient
    public List<Dossierhsupp> getDossierhsuppList() {
        return dossierhsuppList;
    }

    public void setDossierhsuppList(List<Dossierhsupp> dossierhsuppList) {
        this.dossierhsuppList = dossierhsuppList;
    }

    public Compte getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Compte idCompte) {
        this.idCompte = idCompte;
    }

    public Secteur getIdSecteur() {
        return idSecteur;
    }

    public void setIdSecteur(Secteur idSecteur) {
        this.idSecteur = idSecteur;
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
        hash += (idDotation != null ? idDotation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dotationsecteur)) {
            return false;
        }
        Dotationsecteur other = (Dotationsecteur) object;
        if ((this.idDotation == null && other.idDotation != null) || (this.idDotation != null && !this.idDotation.equals(other.idDotation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Dotationsecteur[ idDotation=" + idDotation + " ]";
    }
    
}

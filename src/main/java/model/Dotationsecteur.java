/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "dotationsecteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dotationsecteur.findAll", query = "SELECT d FROM Dotationsecteur d"),
    @NamedQuery(name = "Dotationsecteur.findByIdCompte", query = "SELECT d FROM Dotationsecteur d WHERE d.idCompte = :idCompte"),
    @NamedQuery(name = "Dotationsecteur.findByIdSecteur", query = "SELECT d FROM Dotationsecteur d WHERE d.idSecteur = :idSecteur"),
    @NamedQuery(name = "Dotationsecteur.findByIdDotation", query = "SELECT d FROM Dotationsecteur d WHERE d.idDotation = :idDotation"),
    @NamedQuery(name = "Dotationsecteur.findByMontantInitial", query = "SELECT d FROM Dotationsecteur d WHERE d.montantInitial = :montantInitial"),
    @NamedQuery(name = "Dotationsecteur.findByReliquat", query = "SELECT d FROM Dotationsecteur d WHERE d.reliquat = :reliquat")})
public class Dotationsecteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCompte")
    private int idCompte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idSecteur")
    private int idSecteur;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDotation")
    private Integer idDotation;
    @Column(name = "montantInitial")
    private Double montantInitial;
    @Column(name = "reliquat")
    private Double reliquat;

    public Dotationsecteur() {
    }

    public Dotationsecteur(Integer idDotation) {
        this.idDotation = idDotation;
    }

    public Dotationsecteur(Integer idDotation, int idCompte, int idSecteur) {
        this.idDotation = idDotation;
        this.idCompte = idCompte;
        this.idSecteur = idSecteur;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public int getIdSecteur() {
        return idSecteur;
    }

    public void setIdSecteur(int idSecteur) {
        this.idSecteur = idSecteur;
    }

    public Integer getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(Integer idDotation) {
        this.idDotation = idDotation;
    }

    public Double getMontantInitial() {
        return montantInitial;
    }

    public void setMontantInitial(Double montantInitial) {
        this.montantInitial = montantInitial;
    }

    public Double getReliquat() {
        return reliquat;
    }

    public void setReliquat(Double reliquat) {
        this.reliquat = reliquat;
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

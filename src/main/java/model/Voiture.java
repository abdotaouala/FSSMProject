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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "voiture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Voiture.findAll", query = "SELECT v FROM Voiture v"),
    @NamedQuery(name = "Voiture.findByIdVoiture", query = "SELECT v FROM Voiture v WHERE v.idVoiture = :idVoiture"),
    @NamedQuery(name = "Voiture.findByIdPrixKilo", query = "SELECT v FROM Voiture v WHERE v.idPrixKilo = :idPrixKilo"),
    @NamedQuery(name = "Voiture.findByIdDeplacement", query = "SELECT v FROM Voiture v WHERE v.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Voiture.findByIdIndKm", query = "SELECT v FROM Voiture v WHERE v.idIndKm = :idIndKm"),
    @NamedQuery(name = "Voiture.findByMarque", query = "SELECT v FROM Voiture v WHERE v.marque = :marque"),
    @NamedQuery(name = "Voiture.findByPuissance", query = "SELECT v FROM Voiture v WHERE v.puissance = :puissance")})
public class Voiture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVoiture")
    private Integer idVoiture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPrixKilo")
    private int idPrixKilo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDeplacement")
    private int idDeplacement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idIndKm")
    private int idIndKm;
    @Size(max = 254)
    @Column(name = "marque")
    private String marque;
    @Column(name = "puissance")
    private Integer puissance;

    public Voiture() {
    }

    public Voiture(Integer idVoiture) {
        this.idVoiture = idVoiture;
    }

    public Voiture(Integer idVoiture, int idPrixKilo, int idDeplacement, int idIndKm) {
        this.idVoiture = idVoiture;
        this.idPrixKilo = idPrixKilo;
        this.idDeplacement = idDeplacement;
        this.idIndKm = idIndKm;
    }

    public Integer getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(Integer idVoiture) {
        this.idVoiture = idVoiture;
    }

    public int getIdPrixKilo() {
        return idPrixKilo;
    }

    public void setIdPrixKilo(int idPrixKilo) {
        this.idPrixKilo = idPrixKilo;
    }

    public int getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(int idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public int getIdIndKm() {
        return idIndKm;
    }

    public void setIdIndKm(int idIndKm) {
        this.idIndKm = idIndKm;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Integer getPuissance() {
        return puissance;
    }

    public void setPuissance(Integer puissance) {
        this.puissance = puissance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVoiture != null ? idVoiture.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voiture)) {
            return false;
        }
        Voiture other = (Voiture) object;
        if ((this.idVoiture == null && other.idVoiture != null) || (this.idVoiture != null && !this.idVoiture.equals(other.idVoiture))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Voiture[ idVoiture=" + idVoiture + " ]";
    }
    
}

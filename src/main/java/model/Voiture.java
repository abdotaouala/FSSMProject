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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "voiture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Voiture.findAll", query = "SELECT v FROM Voiture v"),
    @NamedQuery(name = "Voiture.findByIdVoiture", query = "SELECT v FROM Voiture v WHERE v.idVoiture = :idVoiture"),
    @NamedQuery(name = "Voiture.findByMarque", query = "SELECT v FROM Voiture v WHERE v.marque = :marque"),
    @NamedQuery(name = "Voiture.findByPuissance", query = "SELECT v FROM Voiture v WHERE v.puissance = :puissance")})
public class Voiture implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVoiture")
    private Integer idVoiture;
    @Size(max = 254)
    @Column(name = "marque")
    private String marque;
    @Column(name = "puissance")
    private Integer puissance;
    @JoinColumn(name = "idPrixKilo", referencedColumnName = "idPrixKilo")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Prixkilomitrique idPrixKilo;
    @JoinColumns({
        @JoinColumn(name = "idDeplacement", referencedColumnName = "idDeplacement"),
        @JoinColumn(name = "idIndKm", referencedColumnName = "idIndKm")})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Indemntekm indemntekm;

    public Voiture() {
    }

    public Voiture(Integer idVoiture) {
        this.idVoiture = idVoiture;
    }

    public Integer getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(Integer idVoiture) {
        this.idVoiture = idVoiture;
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

    public Prixkilomitrique getIdPrixKilo() {
        return idPrixKilo;
    }

    public void setIdPrixKilo(Prixkilomitrique idPrixKilo) {
        this.idPrixKilo = idPrixKilo;
    }

    public Indemntekm getIndemntekm() {
        return indemntekm;
    }

    public void setIndemntekm(Indemntekm indemntekm) {
        this.indemntekm = indemntekm;
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

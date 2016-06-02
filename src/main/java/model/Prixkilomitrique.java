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
@Table(name = "prixkilomitrique")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prixkilomitrique.findAll", query = "SELECT p FROM Prixkilomitrique p"),
    @NamedQuery(name = "Prixkilomitrique.findByIdPrixKilo", query = "SELECT p FROM Prixkilomitrique p WHERE p.idPrixKilo = :idPrixKilo"),
    @NamedQuery(name = "Prixkilomitrique.findByKmSup", query = "SELECT p FROM Prixkilomitrique p WHERE p.kmSup = :kmSup"),
    @NamedQuery(name = "Prixkilomitrique.findByKmInf", query = "SELECT p FROM Prixkilomitrique p WHERE p.kmInf = :kmInf"),
    @NamedQuery(name = "Prixkilomitrique.findByPrixRoute", query = "SELECT p FROM Prixkilomitrique p WHERE p.prixRoute = :prixRoute"),
    @NamedQuery(name = "Prixkilomitrique.findByPrixPiste", query = "SELECT p FROM Prixkilomitrique p WHERE p.prixPiste = :prixPiste")})
public class Prixkilomitrique implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrixKilo")
    private Integer idPrixKilo;
    @Column(name = "kmSup")
    private Integer kmSup;
    @Column(name = "kmInf")
    private Integer kmInf;
    @Column(name = "prixRoute")
    private Integer prixRoute;
    @Column(name = "prixPiste")
    private Integer prixPiste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrixKilo", fetch = FetchType.EAGER)
    private List<Voiture> voitureList;

    public Prixkilomitrique() {
    }

    public Prixkilomitrique(Integer idPrixKilo) {
        this.idPrixKilo = idPrixKilo;
    }

    public Integer getIdPrixKilo() {
        return idPrixKilo;
    }

    public void setIdPrixKilo(Integer idPrixKilo) {
        this.idPrixKilo = idPrixKilo;
    }

    public Integer getKmSup() {
        return kmSup;
    }

    public void setKmSup(Integer kmSup) {
        this.kmSup = kmSup;
    }

    public Integer getKmInf() {
        return kmInf;
    }

    public void setKmInf(Integer kmInf) {
        this.kmInf = kmInf;
    }

    public Integer getPrixRoute() {
        return prixRoute;
    }

    public void setPrixRoute(Integer prixRoute) {
        this.prixRoute = prixRoute;
    }

    public Integer getPrixPiste() {
        return prixPiste;
    }

    public void setPrixPiste(Integer prixPiste) {
        this.prixPiste = prixPiste;
    }

    @XmlTransient
    public List<Voiture> getVoitureList() {
        return voitureList;
    }

    public void setVoitureList(List<Voiture> voitureList) {
        this.voitureList = voitureList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrixKilo != null ? idPrixKilo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prixkilomitrique)) {
            return false;
        }
        Prixkilomitrique other = (Prixkilomitrique) object;
        if ((this.idPrixKilo == null && other.idPrixKilo != null) || (this.idPrixKilo != null && !this.idPrixKilo.equals(other.idPrixKilo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Prixkilomitrique[ idPrixKilo=" + idPrixKilo + " ]";
    }
    
}

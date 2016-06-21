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
@Table(name = "boncommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boncommande.findAll", query = "SELECT b FROM Boncommande b"),
    @NamedQuery(name = "Boncommande.findByIdBC", query = "SELECT b FROM Boncommande b WHERE b.idBC = :idBC"),
    @NamedQuery(name = "Boncommande.findByIdFournisseur", query = "SELECT b FROM Boncommande b WHERE b.idFournisseur = :idFournisseur"),
    @NamedQuery(name = "Boncommande.findByIdUser", query = "SELECT b FROM Boncommande b WHERE b.idUser = :idUser"),
    @NamedQuery(name = "Boncommande.findByIdDotation", query = "SELECT b FROM Boncommande b WHERE b.idDotation = :idDotation"),
    @NamedQuery(name = "Boncommande.findByDateCommande", query = "SELECT b FROM Boncommande b WHERE b.dateCommande = :dateCommande"),
    @NamedQuery(name = "Boncommande.findByTva", query = "SELECT b FROM Boncommande b WHERE b.tva = :tva"),
    @NamedQuery(name = "Boncommande.findByDateReception", query = "SELECT b FROM Boncommande b WHERE b.dateReception = :dateReception"),
    @NamedQuery(name = "Boncommande.findByEtat", query = "SELECT b FROM Boncommande b WHERE b.etat = :etat"),
    @NamedQuery(name = "Boncommande.findByMontant", query = "SELECT b FROM Boncommande b WHERE b.montant = :montant"),
    @NamedQuery(name = "Boncommande.findByType", query = "SELECT b FROM Boncommande b WHERE b.type = :type")})
public class Boncommande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBC")
    private Integer idBC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFournisseur")
    private int idFournisseur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUser")
    private int idUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDotation")
    private int idDotation;
    @Column(name = "dateCommande")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;
    @Column(name = "tva")
    private Integer tva;
    @Column(name = "dateReception")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReception;
    @Size(max = 254)
    @Column(name = "etat")
    private String etat;
    @Column(name = "montant")
    private Double montant;
    @Size(max = 254)
    @Column(name = "type")
    private String type;

    public Boncommande() {
    }

    public Boncommande(Integer idBC) {
        this.idBC = idBC;
    }

    public Boncommande(Integer idBC, int idFournisseur, int idUser, int idDotation) {
        this.idBC = idBC;
        this.idFournisseur = idFournisseur;
        this.idUser = idUser;
        this.idDotation = idDotation;
    }

    public Integer getIdBC() {
        return idBC;
    }

    public void setIdBC(Integer idBC) {
        this.idBC = idBC;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(int idDotation) {
        this.idDotation = idDotation;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Integer getTva() {
        return tva;
    }

    public void setTva(Integer tva) {
        this.tva = tva;
    }

    public Date getDateReception() {
        return dateReception;
    }

    public void setDateReception(Date dateReception) {
        this.dateReception = dateReception;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

 

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBC != null ? idBC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boncommande)) {
            return false;
        }
        Boncommande other = (Boncommande) object;
        if ((this.idBC == null && other.idBC != null) || (this.idBC != null && !this.idBC.equals(other.idBC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Boncommande[ idBC=" + idBC + " ]";
    }
    
}

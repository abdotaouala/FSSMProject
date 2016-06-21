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
@Table(name = "lignecommande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lignecommande.findAll", query = "SELECT l FROM Lignecommande l"),
    @NamedQuery(name = "Lignecommande.findByIdBC", query = "SELECT l FROM Lignecommande l WHERE l.idBC = :idBC"),
    @NamedQuery(name = "Lignecommande.findByIdArticle", query = "SELECT l FROM Lignecommande l WHERE l.idArticle = :idArticle"),
    @NamedQuery(name = "Lignecommande.findByIdLigne", query = "SELECT l FROM Lignecommande l WHERE l.idLigne = :idLigne"),
    @NamedQuery(name = "Lignecommande.findByQuantite", query = "SELECT l FROM Lignecommande l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignecommande.findByPu", query = "SELECT l FROM Lignecommande l WHERE l.pu = :pu"),
    @NamedQuery(name = "Lignecommande.findByMontant", query = "SELECT l FROM Lignecommande l WHERE l.montant = :montant")})
public class Lignecommande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idBC")
    private int idBC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArticle")
    private int idArticle;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLigne")
    private Integer idLigne;
    @Column(name = "quantite")
    private Integer quantite;
    @Column(name = "pu")
    private Double pu;
    @Column(name = "montant")
    private Integer montant;

    public Lignecommande() {
    }

    public Lignecommande(Integer idLigne) {
        this.idLigne = idLigne;
    }

    public Lignecommande(Integer idLigne, int idBC, int idArticle) {
        this.idLigne = idLigne;
        this.idBC = idBC;
        this.idArticle = idArticle;
    }

    public int getIdBC() {
        return idBC;
    }

    public void setIdBC(int idBC) {
        this.idBC = idBC;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Integer idLigne) {
        this.idLigne = idLigne;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPu() {
        return pu;
    }

    public void setPu(Double pu) {
        this.pu = pu;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLigne != null ? idLigne.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignecommande)) {
            return false;
        }
        Lignecommande other = (Lignecommande) object;
        if ((this.idLigne == null && other.idLigne != null) || (this.idLigne != null && !this.idLigne.equals(other.idLigne))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Lignecommande[ idLigne=" + idLigne + " ]";
    }
    
}

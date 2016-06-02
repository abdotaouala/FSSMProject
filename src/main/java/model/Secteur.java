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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "secteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secteur.findAll", query = "SELECT s FROM Secteur s"),
    @NamedQuery(name = "Secteur.findByIdSecteur", query = "SELECT s FROM Secteur s WHERE s.idSecteur = :idSecteur"),
    @NamedQuery(name = "Secteur.findByIntituleSecteur", query = "SELECT s FROM Secteur s WHERE s.intituleSecteur = :intituleSecteur")})
public class Secteur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSecteur")
    private Integer idSecteur;
    @Size(max = 254)
    @Column(name = "intituleSecteur")
    private String intituleSecteur;
    @JoinColumn(name = "idSecteurP", referencedColumnName = "idSecteurP")
    @ManyToOne(fetch = FetchType.EAGER)
    private Secteurprincipal idSecteurP;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSecteur", fetch = FetchType.EAGER)
    private List<Dotationsecteur> dotationsecteurList;

    public Secteur() {
    }

    public Secteur(Integer idSecteur) {
        this.idSecteur = idSecteur;
    }

    public Integer getIdSecteur() {
        return idSecteur;
    }

    public void setIdSecteur(Integer idSecteur) {
        this.idSecteur = idSecteur;
    }

    public String getIntituleSecteur() {
        return intituleSecteur;
    }

    public void setIntituleSecteur(String intituleSecteur) {
        this.intituleSecteur = intituleSecteur;
    }

    public Secteurprincipal getIdSecteurP() {
        return idSecteurP;
    }

    public void setIdSecteurP(Secteurprincipal idSecteurP) {
        this.idSecteurP = idSecteurP;
    }

    @XmlTransient
    public List<Dotationsecteur> getDotationsecteurList() {
        return dotationsecteurList;
    }

    public void setDotationsecteurList(List<Dotationsecteur> dotationsecteurList) {
        this.dotationsecteurList = dotationsecteurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSecteur != null ? idSecteur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secteur)) {
            return false;
        }
        Secteur other = (Secteur) object;
        if ((this.idSecteur == null && other.idSecteur != null) || (this.idSecteur != null && !this.idSecteur.equals(other.idSecteur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Secteur[ idSecteur=" + idSecteur + " ]";
    }
    
}

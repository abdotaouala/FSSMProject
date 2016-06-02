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
@Table(name = "filiere")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filiere.findAll", query = "SELECT f FROM Filiere f"),
    @NamedQuery(name = "Filiere.findByIdFiliere", query = "SELECT f FROM Filiere f WHERE f.idFiliere = :idFiliere"),
    @NamedQuery(name = "Filiere.findByIntitule", query = "SELECT f FROM Filiere f WHERE f.intitule = :intitule")})
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFiliere")
    private Integer idFiliere;
    @Size(max = 254)
    @Column(name = "intitule")
    private String intitule;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFiliere", fetch = FetchType.EAGER)
    private List<Releve> releveList;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Users idUser;
    @JoinColumn(name = "idDep", referencedColumnName = "idDep")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Departement idDep;
    @JoinColumn(name = "idType", referencedColumnName = "idType")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Typeformations idType;

    public Filiere() {
    }

    public Filiere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    public Integer getIdFiliere() {
        return idFiliere;
    }

    public void setIdFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @XmlTransient
    public List<Releve> getReleveList() {
        return releveList;
    }

    public void setReleveList(List<Releve> releveList) {
        this.releveList = releveList;
    }

    public Users getIdUser() {
        return idUser;
    }

    public void setIdUser(Users idUser) {
        this.idUser = idUser;
    }

    public Departement getIdDep() {
        return idDep;
    }

    public void setIdDep(Departement idDep) {
        this.idDep = idDep;
    }

    public Typeformations getIdType() {
        return idType;
    }

    public void setIdType(Typeformations idType) {
        this.idType = idType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFiliere != null ? idFiliere.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filiere)) {
            return false;
        }
        Filiere other = (Filiere) object;
        if ((this.idFiliere == null && other.idFiliere != null) || (this.idFiliere != null && !this.idFiliere.equals(other.idFiliere))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Filiere[ idFiliere=" + idFiliere + " ]";
    }
    
}

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "typeformations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Typeformations.findAll", query = "SELECT t FROM Typeformations t"),
    @NamedQuery(name = "Typeformations.findByIdType", query = "SELECT t FROM Typeformations t WHERE t.idType = :idType"),
    @NamedQuery(name = "Typeformations.findByNomType", query = "SELECT t FROM Typeformations t WHERE t.nomType = :nomType")})
public class Typeformations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idType")
    private Integer idType;
    @Size(max = 254)
    @Column(name = "nomType")
    private String nomType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idType", fetch = FetchType.EAGER)
    private List<Filiere> filiereList;

    public Typeformations() {
    }

    public Typeformations(Integer idType) {
        this.idType = idType;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    @XmlTransient
    public List<Filiere> getFiliereList() {
        return filiereList;
    }

    public void setFiliereList(List<Filiere> filiereList) {
        this.filiereList = filiereList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idType != null ? idType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Typeformations)) {
            return false;
        }
        Typeformations other = (Typeformations) object;
        if ((this.idType == null && other.idType != null) || (this.idType != null && !this.idType.equals(other.idType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Typeformations[ idType=" + idType + " ]";
    }
    
}

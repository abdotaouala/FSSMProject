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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "departement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departement.findAll", query = "SELECT d FROM Departement d"),
    @NamedQuery(name = "Departement.findByIdDep", query = "SELECT d FROM Departement d WHERE d.idDep = :idDep"),
    @NamedQuery(name = "Departement.findByIdUser", query = "SELECT d FROM Departement d WHERE d.idUser = :idUser"),
    @NamedQuery(name = "Departement.findByNomDep", query = "SELECT d FROM Departement d WHERE d.nomDep = :nomDep")})
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDep")
    private Integer idDep;
    @Column(name = "idUser")
    private Integer idUser;
    @Size(max = 254)
    @Column(name = "nomDep")
    private String nomDep;

    public Departement() {
    }

    public Departement(Integer idDep) {
        this.idDep = idDep;
    }

    public Integer getIdDep() {
        return idDep;
    }

    public void setIdDep(Integer idDep) {
        this.idDep = idDep;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDep != null ? idDep.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departement)) {
            return false;
        }
        Departement other = (Departement) object;
        if ((this.idDep == null && other.idDep != null) || (this.idDep != null && !this.idDep.equals(other.idDep))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Departement[ idDep=" + idDep + " ]";
    }
    
}

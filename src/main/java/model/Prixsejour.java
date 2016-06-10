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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "prixsejour")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prixsejour.findAll", query = "SELECT p FROM Prixsejour p"),
    @NamedQuery(name = "Prixsejour.findByIdPrixSej", query = "SELECT p FROM Prixsejour p WHERE p.idPrixSej = :idPrixSej"),
    @NamedQuery(name = "Prixsejour.findByGrade", query = "SELECT p FROM Prixsejour p WHERE p.grade = :grade"),
    @NamedQuery(name = "Prixsejour.findByPrix", query = "SELECT p FROM Prixsejour p WHERE p.prix = :prix")})
public class Prixsejour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrixSej")
    private Integer idPrixSej;
    @Column(name = "grade")
    private Integer grade;
    @Column(name = "prix")
    private Integer prix;

    public Prixsejour() {
    }

    public Prixsejour(Integer idPrixSej) {
        this.idPrixSej = idPrixSej;
    }

    public Integer getIdPrixSej() {
        return idPrixSej;
    }

    public void setIdPrixSej(Integer idPrixSej) {
        this.idPrixSej = idPrixSej;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrixSej != null ? idPrixSej.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prixsejour)) {
            return false;
        }
        Prixsejour other = (Prixsejour) object;
        if ((this.idPrixSej == null && other.idPrixSej != null) || (this.idPrixSej != null && !this.idPrixSej.equals(other.idPrixSej))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Prixsejour[ idPrixSej=" + idPrixSej + " ]";
    }
    
}

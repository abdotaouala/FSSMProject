/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "bordereauautorisation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bordereauautorisation.findAll", query = "SELECT b FROM Bordereauautorisation b"),
    @NamedQuery(name = "Bordereauautorisation.findByIdBordAut", query = "SELECT b FROM Bordereauautorisation b WHERE b.idBordAut = :idBordAut"),
    @NamedQuery(name = "Bordereauautorisation.findByAnneeUniversitaire", query = "SELECT b FROM Bordereauautorisation b WHERE b.anneeUniversitaire = :anneeUniversitaire")})
public class Bordereauautorisation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBordAut")
    private Integer idBordAut;
    @Column(name = "anneeUniversitaire")
    private Integer anneeUniversitaire;
    @OneToMany(mappedBy = "idBordAut", fetch = FetchType.EAGER)
    private List<Dossiervacataire> dossiervacataireList;
    @OneToMany(mappedBy = "idBordAut", fetch = FetchType.EAGER)
    private List<Dossierhsupp> dossierhsuppList;

    public Bordereauautorisation() {
    }

    public Bordereauautorisation(Integer idBordAut) {
        this.idBordAut = idBordAut;
    }

    public Integer getIdBordAut() {
        return idBordAut;
    }

    public void setIdBordAut(Integer idBordAut) {
        this.idBordAut = idBordAut;
    }

    public Integer getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(Integer anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    @XmlTransient
    public List<Dossiervacataire> getDossiervacataireList() {
        return dossiervacataireList;
    }

    public void setDossiervacataireList(List<Dossiervacataire> dossiervacataireList) {
        this.dossiervacataireList = dossiervacataireList;
    }

    @XmlTransient
    public List<Dossierhsupp> getDossierhsuppList() {
        return dossierhsuppList;
    }

    public void setDossierhsuppList(List<Dossierhsupp> dossierhsuppList) {
        this.dossierhsuppList = dossierhsuppList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBordAut != null ? idBordAut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bordereauautorisation)) {
            return false;
        }
        Bordereauautorisation other = (Bordereauautorisation) object;
        if ((this.idBordAut == null && other.idBordAut != null) || (this.idBordAut != null && !this.idBordAut.equals(other.idBordAut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Bordereauautorisation[ idBordAut=" + idBordAut + " ]";
    }
    
}

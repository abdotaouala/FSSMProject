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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "releve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Releve.findAll", query = "SELECT r FROM Releve r"),
    @NamedQuery(name = "Releve.findByIdRelever", query = "SELECT r FROM Releve r WHERE r.idRelever = :idRelever"),
    @NamedQuery(name = "Releve.findByIdFiliere", query = "SELECT r FROM Releve r WHERE r.idFiliere = :idFiliere"),
    @NamedQuery(name = "Releve.findBySemestre", query = "SELECT r FROM Releve r WHERE r.semestre = :semestre"),
    @NamedQuery(name = "Releve.findByAnneeUniversitaire", query = "SELECT r FROM Releve r WHERE r.anneeUniversitaire = :anneeUniversitaire")})
public class Releve implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRelever")
    private Integer idRelever;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFiliere")
    private int idFiliere;
    @Size(max = 254)
    @Column(name = "semestre")
    private String semestre;
    @Size(max = 254)
    @Column(name = "anneeUniversitaire")
    private String anneeUniversitaire;

    public Releve() {
    }

    public Releve(Integer idRelever) {
        this.idRelever = idRelever;
    }

    public Releve(Integer idRelever, int idFiliere) {
        this.idRelever = idRelever;
        this.idFiliere = idFiliere;
    }

    public Integer getIdRelever() {
        return idRelever;
    }

    public void setIdRelever(Integer idRelever) {
        this.idRelever = idRelever;
    }

    public int getIdFiliere() {
        return idFiliere;
    }

    public void setIdFiliere(int idFiliere) {
        this.idFiliere = idFiliere;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRelever != null ? idRelever.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Releve)) {
            return false;
        }
        Releve other = (Releve) object;
        if ((this.idRelever == null && other.idRelever != null) || (this.idRelever != null && !this.idRelever.equals(other.idRelever))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Releve[ idRelever=" + idRelever + " ]";
    }
    
}

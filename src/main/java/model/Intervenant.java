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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "intervenant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intervenant.findAll", query = "SELECT i FROM Intervenant i"),
    @NamedQuery(name = "Intervenant.findByCinPpr", query = "SELECT i FROM Intervenant i WHERE i.cinPpr = :cinPpr"),
    @NamedQuery(name = "Intervenant.findByNomComplet", query = "SELECT i FROM Intervenant i WHERE i.nomComplet = :nomComplet"),
    @NamedQuery(name = "Intervenant.findByNomArabe", query = "SELECT i FROM Intervenant i WHERE i.nomArabe = :nomArabe"),
    @NamedQuery(name = "Intervenant.findByTelephone", query = "SELECT i FROM Intervenant i WHERE i.telephone = :telephone"),
    @NamedQuery(name = "Intervenant.findByMail", query = "SELECT i FROM Intervenant i WHERE i.mail = :mail")})
public class Intervenant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "cinPpr")
    private String cinPpr;
    @Size(max = 254)
    @Column(name = "nomComplet")
    private String nomComplet;
    @Size(max = 254)
    @Column(name = "nomArabe")
    private String nomArabe;
    @Size(max = 254)
    @Column(name = "telephone")
    private String telephone;
    @Size(max = 254)
    @Column(name = "mail")
    private String mail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinPpr", fetch = FetchType.EAGER)
    private List<Comptebc> comptebcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinPpr", fetch = FetchType.EAGER)
    private List<Dossiervacataire> dossiervacataireList;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Users idUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinPpr", fetch = FetchType.EAGER)
    private List<Deplacement> deplacementList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinPpr", fetch = FetchType.EAGER)
    private List<Dossierhsupp> dossierhsuppList;

    public Intervenant() {
    }

    public Intervenant(String cinPpr) {
        this.cinPpr = cinPpr;
    }

    public String getCinPpr() {
        return cinPpr;
    }

    public void setCinPpr(String cinPpr) {
        this.cinPpr = cinPpr;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getNomArabe() {
        return nomArabe;
    }

    public void setNomArabe(String nomArabe) {
        this.nomArabe = nomArabe;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @XmlTransient
    public List<Comptebc> getComptebcList() {
        return comptebcList;
    }

    public void setComptebcList(List<Comptebc> comptebcList) {
        this.comptebcList = comptebcList;
    }

    @XmlTransient
    public List<Dossiervacataire> getDossiervacataireList() {
        return dossiervacataireList;
    }

    public void setDossiervacataireList(List<Dossiervacataire> dossiervacataireList) {
        this.dossiervacataireList = dossiervacataireList;
    }

    public Users getIdUser() {
        return idUser;
    }

    public void setIdUser(Users idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public List<Deplacement> getDeplacementList() {
        return deplacementList;
    }

    public void setDeplacementList(List<Deplacement> deplacementList) {
        this.deplacementList = deplacementList;
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
        hash += (cinPpr != null ? cinPpr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intervenant)) {
            return false;
        }
        Intervenant other = (Intervenant) object;
        if ((this.cinPpr == null && other.cinPpr != null) || (this.cinPpr != null && !this.cinPpr.equals(other.cinPpr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Intervenant[ cinPpr=" + cinPpr + " ]";
    }
    
}

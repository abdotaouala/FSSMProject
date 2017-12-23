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
@Table(name = "comptebc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comptebc.findAll", query = "SELECT c FROM Comptebc c"),
    @NamedQuery(name = "Comptebc.findByIdCptBc", query = "SELECT c FROM Comptebc c WHERE c.idCptBc = :idCptBc"),
    @NamedQuery(name = "Comptebc.findByCinPpr", query = "SELECT c FROM Comptebc c WHERE c.cinPpr = :cinPpr"),
    @NamedQuery(name = "Comptebc.findByIntitule", query = "SELECT c FROM Comptebc c WHERE c.intitule = :intitule"),
    @NamedQuery(name = "Comptebc.findByBc", query = "SELECT c FROM Comptebc c WHERE c.bc = :bc"),
    @NamedQuery(name = "Comptebc.findByRib", query = "SELECT c FROM Comptebc c WHERE c.rib = :rib")})
public class Comptebc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCptBc")
    private Integer idCptBc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "cinPpr")
    private String cinPpr;
    @Size(max = 254)
    @Column(name = "intitule")
    private String intitule;
    @Size(max = 254)
    @Column(name = "bc")
    private String bc;
    @Size(max = 254)
    @Column(name = "rib")
    private String rib;

    public Comptebc() {
    }

    public Comptebc(Integer idCptBc) {
        this.idCptBc = idCptBc;
    }

    public Comptebc(Integer idCptBc, String cinPpr) {
        this.idCptBc = idCptBc;
        this.cinPpr = cinPpr;
    }

    public Integer getIdCptBc() {
        return idCptBc;
    }

    public void setIdCptBc(Integer idCptBc) {
        this.idCptBc = idCptBc;
    }

    public String getCinPpr() {
        return cinPpr;
    }

    public void setCinPpr(String cinPpr) {
        this.cinPpr = cinPpr;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCptBc != null ? idCptBc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comptebc)) {
            return false;
        }
        Comptebc other = (Comptebc) object;
        if ((this.idCptBc == null && other.idCptBc != null) || (this.idCptBc != null && !this.idCptBc.equals(other.idCptBc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Comptebc[ idCptBc=" + idCptBc + " ]";
    }
    
}

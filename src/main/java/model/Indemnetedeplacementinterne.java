/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mounir
 */
@Entity
@Table(name = "indemnetedeplacementinterne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indemnetedeplacementinterne.findAll", query = "SELECT i FROM Indemnetedeplacementinterne i"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByIdDeplacement", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.indemnetedeplacementinternePK.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByIdIndDep", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.indemnetedeplacementinternePK.idIndDep = :idIndDep"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByMontantDepInt", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.montantDepInt = :montantDepInt")})
public class Indemnetedeplacementinterne implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndemnetedeplacementinternePK indemnetedeplacementinternePK;
    @Column(name = "montantDepInt")
    private Integer montantDepInt;
    @JoinColumn(name = "idPrixRepas", referencedColumnName = "idPrixRepas")
    @ManyToOne(fetch = FetchType.EAGER)
    private Prixrepas idPrixRepas;
    @JoinColumn(name = "idDotation", referencedColumnName = "idDotation")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dotationsecteur idDotation;
    @JoinColumn(name = "idDeplacement", referencedColumnName = "idDeplacement", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Deplacement deplacement;

    public Indemnetedeplacementinterne() {
    }

    public Indemnetedeplacementinterne(IndemnetedeplacementinternePK indemnetedeplacementinternePK) {
        this.indemnetedeplacementinternePK = indemnetedeplacementinternePK;
    }

    public Indemnetedeplacementinterne(int idDeplacement, int idIndDep) {
        this.indemnetedeplacementinternePK = new IndemnetedeplacementinternePK(idDeplacement, idIndDep);
    }

    public IndemnetedeplacementinternePK getIndemnetedeplacementinternePK() {
        return indemnetedeplacementinternePK;
    }

    public void setIndemnetedeplacementinternePK(IndemnetedeplacementinternePK indemnetedeplacementinternePK) {
        this.indemnetedeplacementinternePK = indemnetedeplacementinternePK;
    }

    public Integer getMontantDepInt() {
        return montantDepInt;
    }

    public void setMontantDepInt(Integer montantDepInt) {
        this.montantDepInt = montantDepInt;
    }

    public Prixrepas getIdPrixRepas() {
        return idPrixRepas;
    }

    public void setIdPrixRepas(Prixrepas idPrixRepas) {
        this.idPrixRepas = idPrixRepas;
    }

    public Dotationsecteur getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(Dotationsecteur idDotation) {
        this.idDotation = idDotation;
    }

    public Deplacement getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(Deplacement deplacement) {
        this.deplacement = deplacement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indemnetedeplacementinternePK != null ? indemnetedeplacementinternePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Indemnetedeplacementinterne)) {
            return false;
        }
        Indemnetedeplacementinterne other = (Indemnetedeplacementinterne) object;
        if ((this.indemnetedeplacementinternePK == null && other.indemnetedeplacementinternePK != null) || (this.indemnetedeplacementinternePK != null && !this.indemnetedeplacementinternePK.equals(other.indemnetedeplacementinternePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Indemnetedeplacementinterne[ indemnetedeplacementinternePK=" + indemnetedeplacementinternePK + " ]";
    }
    
}

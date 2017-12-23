/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "indemnetedeplacementinterne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Indemnetedeplacementinterne.findAll", query = "SELECT i FROM Indemnetedeplacementinterne i"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByIdDeplacement", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.indemnetedeplacementinternePK.idDeplacement = :idDeplacement"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByIdIndDep", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.indemnetedeplacementinternePK.idIndDep = :idIndDep"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByIdPrixRepas", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.idPrixRepas = :idPrixRepas"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByIdDotation", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.idDotation = :idDotation"),
    @NamedQuery(name = "Indemnetedeplacementinterne.findByMontantDepInt", query = "SELECT i FROM Indemnetedeplacementinterne i WHERE i.montantDepInt = :montantDepInt")})
public class Indemnetedeplacementinterne implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndemnetedeplacementinternePK indemnetedeplacementinternePK;
    @Column(name = "idPrixRepas")
    private Integer idPrixRepas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDotation")
    private int idDotation;
    @Column(name = "montantDepInt")
    private Integer montantDepInt;

    public Indemnetedeplacementinterne() {
    }

    public Indemnetedeplacementinterne(IndemnetedeplacementinternePK indemnetedeplacementinternePK) {
        this.indemnetedeplacementinternePK = indemnetedeplacementinternePK;
    }

    public Indemnetedeplacementinterne(IndemnetedeplacementinternePK indemnetedeplacementinternePK, int idDotation) {
        this.indemnetedeplacementinternePK = indemnetedeplacementinternePK;
        this.idDotation = idDotation;
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

    public Integer getIdPrixRepas() {
        return idPrixRepas;
    }

    public void setIdPrixRepas(Integer idPrixRepas) {
        this.idPrixRepas = idPrixRepas;
    }

    public int getIdDotation() {
        return idDotation;
    }

    public void setIdDotation(int idDotation) {
        this.idDotation = idDotation;
    }

    public Integer getMontantDepInt() {
        return montantDepInt;
    }

    public void setMontantDepInt(Integer montantDepInt) {
        this.montantDepInt = montantDepInt;
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

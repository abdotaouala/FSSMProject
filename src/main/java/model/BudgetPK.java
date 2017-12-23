/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author user
 */
@Embeddable
public class BudgetPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idCompte")
    private int idCompte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "annee")
    private int annee;

    public BudgetPK() {
    }

    public BudgetPK(int idCompte, int annee) {
        this.idCompte = idCompte;
        this.annee = annee;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCompte;
        hash += (int) annee;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetPK)) {
            return false;
        }
        BudgetPK other = (BudgetPK) object;
        if (this.idCompte != other.idCompte) {
            return false;
        }
        if (this.annee != other.annee) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.BudgetPK[ idCompte=" + idCompte + ", annee=" + annee + " ]";
    }
    
}

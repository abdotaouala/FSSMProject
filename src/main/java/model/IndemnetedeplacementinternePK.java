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
 * @author Mounir
 */
@Embeddable
public class IndemnetedeplacementinternePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idDeplacement")
    private int idDeplacement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idIndDep")
    private int idIndDep;

    public IndemnetedeplacementinternePK() {
    }

    public IndemnetedeplacementinternePK(int idDeplacement, int idIndDep) {
        this.idDeplacement = idDeplacement;
        this.idIndDep = idIndDep;
    }

    public int getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(int idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public int getIdIndDep() {
        return idIndDep;
    }

    public void setIdIndDep(int idIndDep) {
        this.idIndDep = idIndDep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDeplacement;
        hash += (int) idIndDep;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndemnetedeplacementinternePK)) {
            return false;
        }
        IndemnetedeplacementinternePK other = (IndemnetedeplacementinternePK) object;
        if (this.idDeplacement != other.idDeplacement) {
            return false;
        }
        if (this.idIndDep != other.idIndDep) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IndemnetedeplacementinternePK[ idDeplacement=" + idDeplacement + ", idIndDep=" + idIndDep + " ]";
    }
    
}

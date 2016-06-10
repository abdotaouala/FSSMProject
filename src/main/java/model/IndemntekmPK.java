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
public class IndemntekmPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idDeplacement")
    private int idDeplacement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idIndKm")
    private int idIndKm;

    public IndemntekmPK() {
    }

    public IndemntekmPK(int idDeplacement, int idIndKm) {
        this.idDeplacement = idDeplacement;
        this.idIndKm = idIndKm;
    }

    public int getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(int idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public int getIdIndKm() {
        return idIndKm;
    }

    public void setIdIndKm(int idIndKm) {
        this.idIndKm = idIndKm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDeplacement;
        hash += (int) idIndKm;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndemntekmPK)) {
            return false;
        }
        IndemntekmPK other = (IndemntekmPK) object;
        if (this.idDeplacement != other.idDeplacement) {
            return false;
        }
        if (this.idIndKm != other.idIndKm) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IndemntekmPK[ idDeplacement=" + idDeplacement + ", idIndKm=" + idIndKm + " ]";
    }
    
}

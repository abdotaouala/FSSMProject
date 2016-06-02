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
public class IndemnetedeplacementetrangerPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idDeplacement")
    private int idDeplacement;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDepEx")
    private int idDepEx;

    public IndemnetedeplacementetrangerPK() {
    }

    public IndemnetedeplacementetrangerPK(int idDeplacement, int idDepEx) {
        this.idDeplacement = idDeplacement;
        this.idDepEx = idDepEx;
    }

    public int getIdDeplacement() {
        return idDeplacement;
    }

    public void setIdDeplacement(int idDeplacement) {
        this.idDeplacement = idDeplacement;
    }

    public int getIdDepEx() {
        return idDepEx;
    }

    public void setIdDepEx(int idDepEx) {
        this.idDepEx = idDepEx;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDeplacement;
        hash += (int) idDepEx;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndemnetedeplacementetrangerPK)) {
            return false;
        }
        IndemnetedeplacementetrangerPK other = (IndemnetedeplacementetrangerPK) object;
        if (this.idDeplacement != other.idDeplacement) {
            return false;
        }
        if (this.idDepEx != other.idDepEx) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.IndemnetedeplacementetrangerPK[ idDeplacement=" + idDeplacement + ", idDepEx=" + idDepEx + " ]";
    }
    
}

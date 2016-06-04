
package Beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
@ApplicationScoped
@ManagedBean
public class GererBudget {
    private int annee;
    private double montant_RAP;
    private double reliquat_RAP;

    public GererBudget() {
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getMontant_RAP() {
        return montant_RAP;
    }

    public void setMontant_RAP(double montant_RAP) {
        this.montant_RAP = montant_RAP;
    }

    public double getReliquat_RAP() {
        return reliquat_RAP;
    }

    public void setReliquat_RAP(double reliquat_RAP) {
        this.reliquat_RAP = reliquat_RAP;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GererBudget other = (GererBudget) obj;
        if (this.annee != other.annee) {
            return false;
        }
        return true;
    }
     
}

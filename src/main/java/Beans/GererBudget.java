
package Beans;

import DAO.DAOAnneeBudgetaire;
import controlers.ConnexionHibernateUtil;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import models.Anneebudgetaire;
import org.hibernate.SessionFactory;
@ApplicationScoped
@ManagedBean
public class GererBudget {
    private Anneebudgetaire ab;
    public GererBudget() {
    }
    public Anneebudgetaire ajouter(){
    DAOAnneeBudgetaire DAOab=new DAOAnneeBudgetaire(ConnexionHibernateUtil.getSessionFactory());
    return DAOab.add(ab);
    }

    public Anneebudgetaire getAb() {
        return ab;
    }

    public void setAb(Anneebudgetaire ab) {
        this.ab = ab;
    }
}

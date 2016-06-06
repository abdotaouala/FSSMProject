/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Anneebudgetaire;

/**
 *
 * @author Mounir
 */
@Stateless
public class AnneebudgetaireFacade extends AbstractFacade<Anneebudgetaire> {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnneebudgetaireFacade() {
        super(Anneebudgetaire.class);
    }
    
}

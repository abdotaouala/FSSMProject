/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import model.Budget;

/**
 *
 * @author user
 */
@Stateless
public class BudgetFacade extends AbstractFacade<Budget> {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public BudgetFacade() {
        super(Budget.class);
    }
    
    
}

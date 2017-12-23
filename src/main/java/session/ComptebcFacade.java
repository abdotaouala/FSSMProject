/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Comptebc;

/**
 *
 * @author user
 */
@Stateless
public class ComptebcFacade extends AbstractFacade<Comptebc> {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComptebcFacade() {
        super(Comptebc.class);
    }
    
}

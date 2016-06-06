/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Prixsejour;

/**
 *
 * @author Mounir
 */
@Stateless
public class PrixsejourFacade extends AbstractFacade<Prixsejour> {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrixsejourFacade() {
        super(Prixsejour.class);
    }
    
}

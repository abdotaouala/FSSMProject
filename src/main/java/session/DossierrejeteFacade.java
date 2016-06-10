/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Dossierrejete;

/**
 *
 * @author user
 */
@Stateless
public class DossierrejeteFacade extends AbstractFacade<Dossierrejete> {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DossierrejeteFacade() {
        super(Dossierrejete.class);
    }
    
}

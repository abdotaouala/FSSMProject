/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Etatdossier;

/**
 *
 * @author user
 */
@Stateless
public class EtatdossierFacade extends AbstractFacade<Etatdossier> {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtatdossierFacade() {
        super(Etatdossier.class);
    }
    
}

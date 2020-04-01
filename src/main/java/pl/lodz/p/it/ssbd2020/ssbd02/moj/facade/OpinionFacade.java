/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.moj.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;

/**
 *
 * @author student
 */
@Stateless
public class OpinionFacade extends AbstractFacade<Opinion> {

    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OpinionFacade() {
        super(Opinion.class);
    }
    
}

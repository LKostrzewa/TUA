/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.*;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;



/**
 *
 * Klasa fasadowa powiązana z encją AccessLevel
 */
@Stateless
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccessLevelFacade() {
        super(AccessLevel.class);
    }



    @PermitAll
    public AccessLevel findByAccessLevelName(String name) {

        TypedQuery<AccessLevel> tq = em.createNamedQuery("AccessLevel.findByName", AccessLevel.class);
        tq.setParameter("name", name);
        return tq.getSingleResult();
    }

    
}

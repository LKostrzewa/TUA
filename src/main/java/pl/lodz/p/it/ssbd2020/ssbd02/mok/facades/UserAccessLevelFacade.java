/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;

/**
 *
 * Klasa fasadowa powiązana z encją UserAccessLevel
 */
@Stateless
public class UserAccessLevelFacade extends AbstractFacade<UserAccessLevel> {

    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAccessLevelFacade() {
        super(UserAccessLevel.class);
    }

}

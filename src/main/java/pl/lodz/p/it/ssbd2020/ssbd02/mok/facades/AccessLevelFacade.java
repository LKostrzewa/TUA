/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Klasa fasadowa powiązana z encją AccessLevel
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class AccessLevelFacade extends AbstractFacade<AccessLevel> {

    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    @PermitAll
    public AccessLevel findByAccessLevelName(String name) {
        TypedQuery<AccessLevel> typedQuery = entityManager.createNamedQuery("AccessLevel.findByName", AccessLevel.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();
    }
}

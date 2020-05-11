package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @PermitAll
    public AccessLevel findByAccessLevelName(String name) throws AppBaseException {
        TypedQuery<AccessLevel> typedQuery = entityManager.createNamedQuery("AccessLevel.findByName", AccessLevel.class);
        typedQuery.setParameter("name", name);
        try {
            return typedQuery.getSingleResult();
        }
        catch (NoResultException e) {
            throw AppNotFoundException.createAccessLevelNotFoundException(e);
            //throw new AppNotFoundException(AppNotFoundException.ACCESS_LEVEL_MESSAGE_KEY, e);
        }
    }
}

package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją UserAccessLevel
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelFacade extends AbstractFacade<UserAccessLevel> {
    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager entityManager;

    public UserAccessLevelFacade() {
        super(UserAccessLevel.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    /**
     * Metoda, która dodaje krotkę UserAccessLevel do bazy danych.
     *
     * @param entity encja UserAccessLevel.
     */
    @Override
    @RolesAllowed("editUserAccessLevels")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(UserAccessLevel entity) throws AppBaseException {
        super.create(entity);
    }

    /**
     * Metoda, która usuwa krotkę UserAccessLevel z bazy danych.
     *
     * @param entity encja UserAccessLevel.
     */
    @Override
    @RolesAllowed("editUserAccessLevels")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void remove(UserAccessLevel entity) {
        super.remove(entity);
    }

    /**
     * Metoda do pobrania wszystkich encji UserAccessLevel. W aplikacji niewykorzystywana (DenyAll)
     * @return Lista UserAccessLevel
     */
    @Override
    @DenyAll
    public List<UserAccessLevel> findAll() {
        return super.findAll();
    }

    /**
     * Metoda do edycji encji UserAccessLevel. W aplikacji niewykorzystywana (DenyAll)
     * @param entity encja UserAccessLevel do edycji
     */
    @Override
    @DenyAll
    public void edit(UserAccessLevel entity) throws AppBaseException {
        super.edit(entity);
    }

    /**
     * Metoda do pobrania encji UserAccessLevel po identyfikatorze. W aplikacji niewykorzystywana (DenyAll)
     * @param id id encji UserAccessLevel do pobrania
     */
    @Override
    @DenyAll
    public Optional<UserAccessLevel> find(Object id) {
        return super.find(id);
    }
}

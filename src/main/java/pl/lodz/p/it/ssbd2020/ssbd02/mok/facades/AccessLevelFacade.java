package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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

    /**
     * Metoda, która zwraca poziom dostępu o podanej nazwie.
     *
     * @param name nazwa poziomu dostępu.
     * @return encje AccessLevel
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public AccessLevel findByAccessLevelByName(String name) throws AppBaseException {
        TypedQuery<AccessLevel> typedQuery = entityManager.createNamedQuery("AccessLevel.findByName", AccessLevel.class);
        typedQuery.setParameter("name", name);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createAccessLevelNotFoundException(e);
        }
    }

    /**
     * Metoda do pobrania wszystkich poziomów dostępu z bazy danych.
     *
     * @return Lista encji AccessLevel
     */
    @Override
    @RolesAllowed("editUserAccessLevels")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<AccessLevel> findAll() {
        return super.findAll();
    }

    /**
     * Metoda do dodania encji AccessLevel do bazy danych. W aplikacji niewykorzystywana (DenyAll).
     *
     * @param entity encja UserAccessLevel do dodania
     */
    @Override
    @DenyAll
    public void create(AccessLevel entity) throws AppBaseException {
        super.create(entity);
    }

    /**
     * Metoda do edycji encji AccessLevel. W aplikacji niewykorzystywana (DenyAll)
     *
     * @param entity encja AccessLevel do edycji
     */
    @Override
    @DenyAll
    public void edit(AccessLevel entity) throws AppBaseException {
        super.edit(entity);
    }

    /**
     * Metoda do usunięcia encji AccessLevel. W aplikacji niewykorzystywana (DenyAll).
     *
     * @param entity encja UserAccessLevel do usunięcia
     */
    @Override
    @DenyAll
    public void remove(AccessLevel entity) {
        super.remove(entity);
    }

    /**
     * Metoda do pobrania encji AccessLevel po identyfikatorze. W aplikacji niewykorzystywana (DenyAll).
     *
     * @param id id encji AccessLevel do pobrania
     */
    @Override
    @DenyAll
    public Optional<AccessLevel> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która blokuje encje z podanym typem blokady .
     *
     * @param entity blokowana encja
     * @param lockModeType typ blokady
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @DenyAll
    public void lock(AccessLevel entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

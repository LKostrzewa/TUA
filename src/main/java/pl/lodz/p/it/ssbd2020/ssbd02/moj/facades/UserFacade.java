package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
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
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją User.
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager entityManager;

    public UserFacade() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda, która tworzy encję.
     *
     * @param entity tworzona encja
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @DenyAll
    @Override
    public void create(User entity) throws AppBaseException {
        super.create(entity);
    }

    /**
     * Metoda, która znajduje wszystkie encje.
     *
     * @return lista encji
     */
    @DenyAll
    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która zwraca encję o podanym identyfikatorze.
     *
     * @param id identyfikator encji
     * @return encja T
     */
    @DenyAll
    @Override
    public Optional<User> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która edytuje encję.
     *
     * @param user modyfikowana encja
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @DenyAll
    @Override
    public void edit(User user) throws AppBaseException {
        super.edit(user);
    }

    /**
     * Metoda, która zwraca użytkownika o podanej nazwie.
     *
     * @param userLogin login użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addRental")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User findByLogin(String userLogin) throws AppBaseException {
        try {
            return getEntityManager().createNamedQuery("User.findByLogin", User.class)
                    .setParameter("login", userLogin).getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createUserNotFoundException(e);
        }
    }

    /**
     * Metoda, która usuwa encję.
     *
     * @param entity usuwana encja
     */
    @DenyAll
    @Override
    public void remove(User entity) {
        super.remove(entity);
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
    public void lock(User entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

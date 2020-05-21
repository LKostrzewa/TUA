package pl.lodz.p.it.ssbd2020.ssbd02.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppPersistenceException;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Fasadowa klasa abstrakcyjna po której dziedziczą wszystkie inne klasy fasadowe
 */
public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Bazowa metoda, która tworzy encje.
     *
     * @param entity tworzona encja
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public void create(T entity) throws AppBaseException {
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (PersistenceException e) {
            throw AppPersistenceException.createAppPersistenceException(entity, e);
        } catch (ConstraintViolationException e) {
            throw AppConstraintViolationException.createAppConstraintViolationException(entity,e);
        }
    }

    /**
     * Bazowa metoda, która edytuje encje.
     *
     * @param entity modyfikowana encja
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public void edit(T entity) throws AppBaseException {
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (OptimisticLockException e) {
            throw AppOptimisticLockException.createAppOptimisticLockException(entity, e);
        } catch (PersistenceException e) {
            throw AppPersistenceException.createAppPersistenceException(entity, e);
        } catch (ConstraintViolationException e) {
            throw AppConstraintViolationException.createAppConstraintViolationException(entity,e);
        }
    }

    /**
     * Bazowa metoda, która usuwa encje.
     *
     * @param entity usuwana encja
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Bazowa metoda, która zwraca encje o podanym identyfikatorze.
     *
     * @param id identyfikator encji
     * @return encja T
     */
    public Optional<T> find(Object id) {
        return Optional.ofNullable(getEntityManager().find(entityClass, id));
    }

    /**
     * Bazowa metoda, która znajduje wszystkie encje.
     *
     * @@return lista encji
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }


}

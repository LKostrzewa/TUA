package pl.lodz.p.it.ssbd2020.ssbd02.facades;

import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * Fasadowa klasa abstrakcyjna po której dziedziczą wszystkie inne klasy fasadowe.
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
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    public void create(T entity) throws AppBaseException {
        try {
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (PersistenceException e) {
            throw AppPersistenceException.createAppPersistenceException(entity, e);
        } catch (ConstraintViolationException e) {
            throw AppConstraintViolationException.createAppConstraintViolationException(entity, e);
        } catch (DatabaseException e) {
            throw AppDatabaseException.createAppDatabaseException(entity, e);
        }
    }

    /**
     * Bazowa metoda, która edytuje encje.
     *
     * @param entity modyfikowana encja
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
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
            throw AppConstraintViolationException.createAppConstraintViolationException(entity, e);
        } catch (DatabaseException e) {
            throw AppDatabaseException.createAppDatabaseException(entity, e);
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
     * @return lista encji
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Bazowa metoda, która blokuje encje z podanym typem blokady .
     *
     * @param entity blokowana encja
     * @param lockModeType typ blokady
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    public void lock(T entity, LockModeType lockModeType) throws AppBaseException{
        try {
            getEntityManager().lock(entity, lockModeType);
        } catch (OptimisticLockException e) {
            throw AppOptimisticLockException.createAppOptimisticLockException(entity, e);
        }
    }
}

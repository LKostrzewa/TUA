package pl.lodz.p.it.ssbd2020.ssbd02.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppOptimisticLockException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;

/**
 * Fasadowa klasa abstrakcyjna po której dziedziczą wszystkie inne klasy fasadowe
 */
public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void edit(T entity) throws AppBaseException {
        try {
            getEntityManager().merge(entity);
            getEntityManager().flush();
        } catch (OptimisticLockException e) {
            //throw new AppOptimisticLockException("exception.optimisticLock", e);
            throw AppOptimisticLockException.createAppOptimisticLockException(entity, e);
        }

    }


    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public Optional<T> find(Object id) {
        return Optional.ofNullable(getEntityManager().find(entityClass, id));
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    // TODO czy będziemy tego używać?
    public void flush() throws AppBaseException {
        try {
            getEntityManager().flush();
        } catch (OptimisticLockException e) {
            throw new AppBaseException("exception.optimisticLock");

        }
    }
}

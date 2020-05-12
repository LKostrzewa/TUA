package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa fasadowa powiązana z encją User
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

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    @RolesAllowed("lockAccount")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która edytuje encje user.
     *
     * @param user encja użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(User user) throws AppBaseException {
        super.edit(user);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(User user) {
        super.create(user);
    }

    /**
     * Metoda, która zwraca użytkownika o podanym loginie.
     *
     * @param userLogin login użytkownika.
     * @return encje User
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User findByLogin(String userLogin) throws AppBaseException {
        try {
            return getEntityManager().createNamedQuery("User.findByLogin", User.class)
                    .setParameter("login",userLogin).getSingleResult();
        } catch (NoResultException e){
            throw AppNotFoundException.createUserNotFoundException(e);
        }
    }

    public boolean existByLogin(String login) {
        return entityManager.createNamedQuery("User.countByLogin", Long.class)
                .setParameter("login", login).getSingleResult() > 0;
    }

    public boolean existByEmail(String email) {
        return entityManager.createNamedQuery("User.countByEmail", Long.class)
                .setParameter("email", email).getSingleResult() > 0;
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User findByEmail(String email) throws AppBaseException {
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByEmail", User.class);
        typedQuery.setParameter("email", email);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e){
            throw AppNotFoundException.createUserNotFoundException(e);
        }
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User findByResetPasswordCode(String resetPasswordCode) throws AppBaseException {
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByResetPasswordCode", User.class);
        typedQuery.setParameter("resetPasswordCode", resetPasswordCode);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e){
            throw AppNotFoundException.createUserNotFoundException(e);
        }
    }

    @PermitAll
    public User findByActivationCode(String activationCode) {
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByActivationCode", User.class);
        typedQuery.setParameter("activationCode", activationCode);
        return typedQuery.getSingleResult();
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void flush() throws AppBaseException {
        super.flush();
    }

    public List<User> getResultList(int start, int size,
                                    Map<String, FilterMeta> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        CriteriaQuery<User> select = criteriaQuery.select(root);

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, FilterMeta> entry : filters.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue().getFilterValue();
                if (value == null) {
                    continue;
                }

                Expression<String> expression = root.get(field).as(String.class);
                Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(expression),
                        "%" + value.toString().toLowerCase() + "%");
                predicates.add(predicate);
            }
            if (predicates.size() > 0) {
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray
                        (new Predicate[0])));
            }
        }

        return entityManager.createQuery(select).setFirstResult(start).setMaxResults(size).getResultList();
    }
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(root));

        if (filters != null && filters.size() > 0) {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, FilterMeta> entry : filters.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue().getFilterValue();
                if (value == null) {
                    continue;
                }

                Expression<String> expression = root.get(field).as(String.class);
                Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(expression),
                        "%" + value.toString().toLowerCase() + "%");
                predicates.add(predicate);
            }
            if (predicates.size() > 0) {
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            }
        }
        Long count = entityManager.createQuery(select).getSingleResult();
        return count.intValue();
    }
}


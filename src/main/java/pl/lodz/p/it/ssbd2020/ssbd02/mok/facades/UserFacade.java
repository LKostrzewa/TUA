package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /**
     * Metoda do pobrania wszystkich encji User.
     *
     * @return Lista User
     */
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @RolesAllowed({"getUserReport","TIME"})
    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która zwraca użytkownika o podanym identyfikatorze.
     *
     * @param id identyfikator użytkownika
     * @return encja User
     */
    @Override
    @RolesAllowed({"lockAccount","findUserAccessLevelById", "getEditUserDtoById", "unlockAccount"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<User> find(Object id) {
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

    /**
     * Metoda, dodaje podanego użytkownika do bazy danych.
     *
     * @param user encja użytkownika do dodania do bazy.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(User user) throws AppBaseException {
            super.create(user);
    }

    /**
     * Metoda, która zwraca aktualnie zalogowanego użytkownika
     *
     * @return encja User
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

    /**
     * Metoda, sprawdza czy istnieje użytkownik w bazie o danym loginie poprzez sprawdzenie czy rezultat wykonania
     * zapytania COUNT jest większy od 0.
     *
     * @param login login użytkownika.
     * @return true/false zależnie czy użytkownik z danym loginem istnieje lub nie
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean existByLogin(String login) {
        return entityManager.createNamedQuery("User.countByLogin", Long.class)
                .setParameter("login", login).getSingleResult() > 0;
    }
    /**
     * Metoda, sprawdza czy istnieje użytkownik w bazie o danym emailu poprzez sprawdzenie czy rezultat wykonania
     * zapytania COUNT jest większy od 0.
     *
     * @param email email użytkownika.
     * @return true/false zależnie czy użytkownik z danym emailem istnieje lub nie
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean existByEmail(String email) {
        return entityManager.createNamedQuery("User.countByEmail", Long.class)
                .setParameter("email", email).getSingleResult() > 0;
    }

    /**
     * Metoda, która zwraca użytkownika o podanym emailu
     *
     * @param email szukany email
     * @return encja User
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
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

    /**
     * Metoda, która zwraca użytkownika o podanym kodzie do resetowania hasła
     *
     * @param resetPasswordCode szukany kod do resetowania hasła
     * @return encja User
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
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

    /**
     * Metoda, która zwraca użytkownika o podanym kodzie do aktywacyjnym
     *
     * @param activationCode szukany kod aktywacyjny
     * @return encja User
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User findByActivationCode(String activationCode) throws AppBaseException {
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByActivationCode", User.class);
        typedQuery.setParameter("activationCode", activationCode);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createUserNotFoundException(e);
        }
    }

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param start   numer pierwszego obiektu
     * @param size    rozmiar strony
     * @param filters para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed("getResultList")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
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
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("login")));

        return entityManager.createQuery(select).setFirstResult(start).setMaxResults(size).getResultList();
    }

    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCount")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
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

    /**
     * Metoda do usuwania encji user.
     * @param entity encja użytkownika do usunięcia
     */
    @RolesAllowed("TIME")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void remove(User entity) {
        super.remove(entity);
    }

}


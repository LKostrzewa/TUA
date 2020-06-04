package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
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
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją Rental.
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalFacade extends AbstractFacade<Rental> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public RentalFacade() {
        super(Rental.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda, która zwraca listę wszystkich wypożyczeń.
     *
     * @return lista wypożyczeń
     */
    @Override
    @RolesAllowed({"getRentals","updateRentalStatus"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Rental> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która służy do wyszukania obiektu po kluczu głównym.
     *
     * @param id wartość klucza głównego
     * @return optional z wyszukanym obiektem encji lub pusty, jeśli poszukiwany obiekt encji nie istnieje
     */
    @Override
    @RolesAllowed({"getUserRentalDetails", "cancelRental"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Rental> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która sprawdza czy wypożyczenie danego jachtu koliduje z innymi wypożyczeniami w bazie,
     * poprzez sprawdzenie czy rezultat wykonania zapytania COUNT jest większy od 0.
     *
     * @param rental encja wypożyczenia
     * @return true/false zależnie czy okres trwania danego wypożyczenia koliduje z innymi wypożyczeniami
     */
    @RolesAllowed("addRental")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean interfere(Rental rental) {
        return entityManager.createNamedQuery("Rental.findBetweenDatesWithYacht", Long.class)
                .setParameter("name", rental.getYacht().getName())
                .setParameter("endDate", rental.getEndDate())
                .setParameter("beginDate", rental.getBeginDate()).getSingleResult() > 0;
    }

    /**
     * Metoda, która edytuje encję wypożyczenia.
     *
     * @param rental encja wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"cancelRental","updateRentalStatus"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(Rental rental) throws AppBaseException {
        super.edit(rental);
    }

    /**
     * Metoda, która dodaje encję nowego wypożyczenia.
     *
     * @param rental obiekt encji z danymi nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("addRental")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Rental rental) throws AppBaseException {
        super.create(rental);
    }

    /**
     * Metoda, która usuwa encje.
     *
     * @param entity usuwana encja
     */
    @Override
    @DenyAll
    public void remove(Rental entity) {
        super.remove(entity);
    }

    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCountRental")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Rental> root = criteriaQuery.from(Rental.class);
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
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed("getResultListRental")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Rental> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rental> criteriaQuery = criteriaBuilder.createQuery(Rental.class);
        Root<Rental> root = criteriaQuery.from(Rental.class);
        CriteriaQuery<Rental> select = criteriaQuery.select(root);

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
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));

        return entityManager.createQuery(select).setFirstResult(first).setMaxResults(pageSize).getResultList();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Klasa fasadowa powiązana z encją Rental.
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalFacade extends AbstractFacade<Rental> {
    private final PropertyReader propertyReader = new PropertyReader();
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;
    private String RENTAL_CANCELLED_STATUS;

    public RentalFacade() {
        super(Rental.class);
    }

    @PostConstruct
    public void init() {
        RENTAL_CANCELLED_STATUS = propertyReader.getPropertyWithoutLocale("config", "CANCELLED_STATUS");
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
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @PermitAll
    public List<Rental> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która służy do wyszukania obiektu po kluczu biznesowym.
     *
     * @param businessKey wartość klucza biznesowego
     * @return optional z wyszukanym obiektem encji lub pusty, jeśli poszukiwany obiekt encji nie istnieje
     */
    @RolesAllowed({"getUserRentalDetails", "cancelRental", "addOpinion"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Rental> findByBusinessKey(UUID businessKey) {
        return Optional.ofNullable(entityManager.createNamedQuery("Rental.findByBusinessKey", Rental.class)
                .setParameter("businessKey", businessKey).getSingleResult());
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
                .setParameter("beginDate", rental.getBeginDate())
                .setParameter("rentalStatusName", RENTAL_CANCELLED_STATUS).getSingleResult() > 0;
    }

    /**
     * Metoda, która edytuje encję wypożyczenia.
     *
     * @param rental encja wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @PermitAll
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
     * Metoda, która usuwa encje.
     *
     * @param id klucz główny
     * @return optional z wyszukanym obiektem encji lub pusty, jeśli poszukiwany obiekt encji nie istnieje
     */
    @Override
    @DenyAll
    public Optional<Rental> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która pobiera z bazy wszystkie wypożyczenia, w których nazwa jachtu pasuje
     * do przekazanego ciągu znaków
     *
     * @param filter ciąg znaków według którego będą filtrowane wypożyczenia
     * @return lista wszystkich wypożyczeń, których nazwa jachtu pasuje do wzorca
     */
    @RolesAllowed("getFilteredRentals")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Rental> getFilteredRentals(String filter) {
        return entityManager.createNamedQuery("Rental.findByYachtName", Rental.class)
                .setParameter("name", '%' + filter.toLowerCase() + '%').getResultList();
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
    public void lock(Rental entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

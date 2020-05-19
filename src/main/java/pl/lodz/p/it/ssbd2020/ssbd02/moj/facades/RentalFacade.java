package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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

    @Override
    @DenyAll
    public void create(Rental entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void edit(Rental entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @DenyAll
    public void remove(Rental entity) {
        super.remove(entity);
    }

    /**
     * Metoda, która zwraca listę wszystkich wypożyczeń.
     *
     * @return lista wypożyczeń
     */
    @Override
    @RolesAllowed({"getRentals","getAllRentals"})
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
    @RolesAllowed("getRentalById")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Rental> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która zwraca wszystkie wypożyczenia na dany jacht.
     *
     * @param yachtName nazwa yachtu
     * @return lista wypożyczeń użytkownika o podanym loginie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getRentalsByYacht")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Rental> findAllByYacht(String yachtName) throws AppBaseException {
        TypedQuery<Rental> typedQuery = entityManager.createNamedQuery("Rental.findByYachtName", Rental.class);
        typedQuery.setParameter("name", yachtName);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e){
            throw AppNotFoundException.createYachtNotFoundException(e);
        }
    }
}

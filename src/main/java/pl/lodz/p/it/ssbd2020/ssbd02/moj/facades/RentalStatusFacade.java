package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
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
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją RentalStatus.
 */
@Stateless(name = "RentalStatusFacade")
@Named("RentalStatusFacade")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalStatusFacade extends AbstractFacade<RentalStatus> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public RentalStatusFacade() {
        super(RentalStatus.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda, która zwraca status wypożyczenia o podanej nazwie.
     *
     * @param statusName Nazwa statusu wypożyczenia
     * @return Encja statusu wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"cancelRental", "addRental"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public RentalStatus findByName(String statusName) throws AppBaseException {
        try {
            return getEntityManager().createNamedQuery("RentalStatus.findByName", RentalStatus.class)
                    .setParameter("name", statusName).getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createRentalStatusNotFoundException(e);
        }
    }

    /**
     * Metoda, która edytuje encje.
     *
     * @param rentalStatus modyfikowana encja
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @DenyAll
    public void edit(RentalStatus rentalStatus) throws AppBaseException {
        super.edit(rentalStatus);
    }

    /**
     * Metoda, która tworzy encje.
     *
     * @param entity tworzona encja
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @DenyAll
    public void create(RentalStatus entity) throws AppBaseException {
        super.create(entity);
    }

    /**
     * Metoda, która usuwa encje.
     *
     * @param entity usuwana encja
     */
    @Override
    @DenyAll
    public void remove(RentalStatus entity) {
        super.remove(entity);
    }

    /**
     * Metoda, która zwraca encje o podanym identyfikatorze.
     *
     * @param id identyfikator encji
     * @return encja RentalStatus
     */
    @Override
    @DenyAll
    public Optional<RentalStatus> find(Object id) {
        return super.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @PermitAll
    public List<RentalStatus> findAll() {
        return super.findAll();
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
    public void lock(RentalStatus entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
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
import java.util.List;
import java.util.Optional;

@Stateless
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
     * Metoda, która zwraca obiekt RentalStatus o podanej nazwie.
     *
     * @param statusName Nazwa statusu wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("cancelRental")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public RentalStatus findByName(String statusName) throws AppBaseException {
        try {
            return getEntityManager().createNamedQuery("RentalStatus.findByName", RentalStatus.class)
                    .setParameter("name", statusName).getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createRentalNotFoundException(e);
        }
    }

    @Override
    @DenyAll
    public void edit(RentalStatus rentalStatus) throws AppBaseException {
        super.edit(rentalStatus);
    }

    @Override
    @DenyAll
    public void create(RentalStatus entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @DenyAll
    public void remove(RentalStatus entity) {
        super.remove(entity);
    }

    @Override
    @DenyAll
    public Optional<RentalStatus> find(Object id) {
        return super.find(id);
    }

    @Override
    @DenyAll
    public List<RentalStatus> findAll() {
        return super.findAll();
    }
}

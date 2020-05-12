package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtFacade extends AbstractFacade<Yacht> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public YachtFacade() {
        super(Yacht.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(Yacht entity) {
        super.create(entity);
    }

    @Override
    public void edit(Yacht entity) throws AppBaseException {
        super.edit(entity);
    }

    /**
     * Metoda, która zwraca yacht o podanym od.
     *
     * @param id id jachtu.
     * @return yacht dto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("getYachtById")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Yacht find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która zwraca wszystkie jachty
     *
     * @return lista jachtów
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("getAllYachts")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Yacht> findAll() {
        return super.findAll();
    }
}

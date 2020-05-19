package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
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
import java.util.Optional;

@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class PortFacade extends AbstractFacade<Port> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public PortFacade() {
        super(Port.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    /**
     * Metoda, która służy do wyszukania obiektu portu po kluczu głównym.
     *
     * @param id wartość klucza głównego
     * @return optional z wyszukanym obiektem encji lub pusty, jeśli poszukiwany obiekt encji nie istnieje
     */
    @Override
    @RolesAllowed({"getPortById","getAllYachtsByPort","assignYachtToPort", "retractYachtToPort"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Port> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która zwraca listę portów.
     *
     * @return lista portów.
     */
    @Override
    @RolesAllowed("getAllPorts")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Port> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która edytuje encje port.
     *
     * @param entity encja portu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"assignYachtToPort", "retractYachtToPort"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(Port entity) throws AppBaseException {
        super.edit(entity);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją Port.
 */
@Stateless(name = "PortFacade")
@Named("PortFacade")
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
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Port> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która zwraca listę wszystkich portów.
     *
     * @return lista portów
     */
    @Override
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Port> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, dodaje podany port do bazy danych.
     *
     * @param port encja portu do dodania do bazy
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Port port) throws AppBaseException {
        super.create(port);
    }

    /**
     * Metoda, która edytuje encje portu.
     *
     * @param port encja portu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(Port port) throws AppBaseException {
        super.edit(port);
    }

    /**
     * Metoda, która usuwa encje portu z bazy.
     *
     * @param port encja jacht
     */
    @Override
    @DenyAll
    public void remove(Port port) {
        super.remove(port);
    }

    /**
     * Metoda, sprawdza czy istnieje port w bazie o danej nazwie poprzez sprawdzenie czy rezultat wykonania
     * zapytania COUNT jest większy od 0.
     *
     * @param name nazwa portu
     * @return true/false zależnie czy port o danej nazwie istnieje lub nie
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean existByName(String name) {
        return entityManager.createNamedQuery("Port.countByName", Long.class)
                .setParameter("name", name).getSingleResult().intValue() > 0;
    }

    /**
     * Metoda, która blokuje encje z podanym typem blokady .
     *
     * @param entity blokowana encja
     * @param lockModeType typ blokady
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void lock(Port entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

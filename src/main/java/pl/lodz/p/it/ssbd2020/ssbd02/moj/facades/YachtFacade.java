package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
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
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją Yacht.
 */
@Stateless(name = "YachtFacade")
@Named("YachtFacade")
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

    /**
     * Metoda, dodaje podany jacht do bazy danych.
     *
     * @param yacht encja jachtu do dodania do bazy
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Yacht yacht) throws AppBaseException {
        super.create(yacht);
    }

    /**
     * Metoda, która edytuje encje jacht.
     *
     * @param yacht encja jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(Yacht yacht) throws AppBaseException {
        super.edit(yacht);
    }

    /**
     * Metoda, która zwraca yacht o podanym id.
     *
     * @param id id jachtu.
     * @return optional yacht
     */
    @Override
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Yacht> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda, która zwraca wszystkie jachty
     *
     * @return lista jachtów
     */
    @Override
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Yacht> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, która usuwa encje jacht z bazy.
     *
     * @param yacht encja jacht.
     */
    @Override
    @DenyAll
    public void remove(Yacht yacht) {
        super.remove(yacht);
    }

    /**
     * Metoda, sprawdza czy istnieje jacht w bazie o danej nazwie poprzez sprawdzenie czy rezultat wykonania
     * zapytania COUNT jest większy od 0.
     *
     * @param name nazwa jachtu.
     * @return true/false zależnie czy użytkownik z danym loginem istnieje lub nie
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean existByName(String name) {
        return entityManager.createNamedQuery("Yacht.countByName", Long.class)
                .setParameter("name", name).getSingleResult() > 0;
    }

    /**
     * Metoda, która zwraca jacht o podanej nazwie.
     *
     * @param yachtName Nazwa jachtu
     * @return znaleziona encja jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Yacht findByName(String yachtName) throws AppBaseException {
        try {
            return getEntityManager().createNamedQuery("Yacht.findByName", Yacht.class)
                    .setParameter("name", yachtName).getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createYachtNotFoundException(e);
        }
    }

    /**
     * Metoda, która blokuje encje z podanym typem blokady.
     *
     * @param entity blokowana encja
     * @param lockModeType typ blokady
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void lock(Yacht entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

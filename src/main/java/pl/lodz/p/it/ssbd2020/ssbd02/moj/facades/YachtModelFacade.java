package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
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
public class YachtModelFacade extends AbstractFacade<YachtModel> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public YachtModelFacade() {
        super(YachtModel.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda, dodaje podany model jachtu do bazy danych.
     *
     * @param entity encja modelu jachtu do dodania do bazy.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("addYachtModel")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(YachtModel entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    public void edit(YachtModel entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    public void remove(YachtModel entity) {
        super.remove(entity);
    }

    @Override
    @RolesAllowed("addYacht")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<YachtModel> find(Object id) {
        return super.find(id);
    }

    @Override
    public List<YachtModel> findAll() {
        return super.findAll();
    }

    /**
     * Metoda, sprawdza czy istnieje model jachtu w bazie o danym ? poprzez sprawdzenie czy rezultat wykonania
     * zapytania COUNT jest większy od 0.
     *
     * @param model nazwa jachtu.
     * @return true/false zależnie czy użytkownik z danym loginem istnieje lub nie
     */
    @RolesAllowed("addYachtModel")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean existByModel(String model) {
        return entityManager.createNamedQuery("YachtModel.countByModel", Long.class)
                .setParameter("model", model).getSingleResult() > 0;
    }
}

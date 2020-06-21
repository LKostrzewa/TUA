package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
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
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Klasa fasadowa powiązana z encją Opinion.
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class OpinionFacade extends AbstractFacade<Opinion> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public OpinionFacade() {
        super(Opinion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Metoda, która dodaje nową opinię.
     *
     * @param entity encja z nową opinią do dodania
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("addOpinion")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Opinion entity) throws AppBaseException {
        super.create(entity);
    }

    /**
     * Metoda służąca do zapisu nowej wersji opinii.
     *
     * @param entity encja z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("editOpinion")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(Opinion entity) throws AppBaseException {
        super.edit(entity);
    }

    /**
     * Metoda, która usuwa encje.
     *
     * @param entity usuwana encja
     */
    @Override
    @DenyAll
    public void remove(Opinion entity) {
        super.remove(entity);
    }

    /**
     * Metoda zwracająca opinię na podstawie przekazanego identyfikatora.
     *
     * @param id identyfikator opinii
     * @return Optional typu Opinion
     */
    @Override
    @DenyAll
    public Optional<Opinion> find(Object id) {
        return super.find(id);
    }


    /**
     * Metoda, która znajduje wszystkie encje.
     *
     * @return lista encji
     */
    @Override
    @DenyAll
    public List<Opinion> findAll() {
        return super.findAll();
    }

    /**
     * Metoda pobierająca wszystkie opinie przypisane do danego jachtu.
     *
     * @param yachtId identyfikator jachtu
     * @return lista opini dla danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli yacht nie zostanie znaleziony
     */
    @RolesAllowed({"addOpinion", "editOpinion"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Opinion> getAllOpinionsByYacht(Long yachtId) throws AppBaseException {
        TypedQuery<Opinion> typedQuery = entityManager.createNamedQuery("Opinion.findAllByYacht", Opinion.class);
        typedQuery.setParameter("id", yachtId);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            throw AppNotFoundException.createYachtNotFoundException(e);
        }
    }

    /**
     * Metoda zwracająca opinię na podstawie przekazanego klucza biznesowego.
     *
     * @param rentalBusinessKey klucz biznesowy opinii
     * @return Znaleziona opinia
     * @throws AppBaseException wyjątek aplikacyjny, jesli opinia nie zostanie znaleziona
     */
    @RolesAllowed("getOpinionByBusinessKey")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Opinion getOpinionByRentalBusinessKey(String rentalBusinessKey) throws AppBaseException{
        TypedQuery<Opinion> typedQuery = entityManager.createNamedQuery("Opinion.findByRentalBusinessKey", Opinion.class);
        typedQuery.setParameter("businessKey", UUID.fromString(rentalBusinessKey));
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AppNotFoundException.createOpinionNotFoundException(e);
        }
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
    public void lock(Opinion entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

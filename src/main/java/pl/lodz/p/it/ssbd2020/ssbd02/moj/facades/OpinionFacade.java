package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
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
public class OpinionFacade extends AbstractFacade<Opinion> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public OpinionFacade() {
        super(Opinion.class);
    }

    /**
     * Metoda, która dodaje nową opinię
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
     * Metoda służąca do zapisu nowej wersji opinii
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

    @Override
    @DenyAll
    public void remove(Opinion entity) {
        super.remove(entity);
    }

    /**
     * Metoda zwracająca opinię na podstawie przekazanego identyfikatora
     *
     * @param id identyfikator opinii
     * @return Optional typu Opinion
     */
    @Override
    @RolesAllowed("getOpinionById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Optional<Opinion> find(Object id){
        return super.find(id);
    }

    @Override
    @DenyAll
    public List<Opinion> findAll() {
        return super.findAll();
    }

    /**
     * Metoda pobierająca wszystkie opinie przypisane do danego jachtu
     *
     * @param yachtId identyfikator jachtu
     * @return lista opini dla danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli yacht nie zostanie znaleziony
     */
    @RolesAllowed("getAllOpinionsByYacht")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Opinion> getAllOpinionsByYacht(Long yachtId) throws AppBaseException {
        TypedQuery<Opinion> typedQuery = entityManager.createNamedQuery("Opinion.findAllByYacht", Opinion.class);
        typedQuery.setParameter("id", yachtId);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e){
            throw AppNotFoundException.createYachtNotFoundException(e);
        }
    }
}

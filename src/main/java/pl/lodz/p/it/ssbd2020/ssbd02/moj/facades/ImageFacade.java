package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
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
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * Klasa fasadowa powiązana z encją Image.
 */
@Stateless(name = "ImageFacade")
@Named("ImageFacade")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageFacade extends AbstractFacade<Image> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public ImageFacade() {
        super(Image.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    /**
     * Metoda która dodaje nowe zdjęcie do bazy.
     *
     * @param entity tworzona encja
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @Override
    @RolesAllowed("addImage")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Image entity) throws AppBaseException {
        super.create(entity);
    }

    /**
     * Metoda która usuwa zdjęcie z bazy.
     *
     * @param entity usuwana encja
     */
    @Override
    @RolesAllowed("deleteImage")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void remove(Image entity) {
        super.remove(entity);
    }


    /**
     * Metoda która zwraca zdjęcie na podstawie przekazanego identyfikatora.
     *
     * @param id identyfikator encji
     * @return szukana encja
     */
    @Override
    @RolesAllowed({"deleteImage", "getImageById"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Image> find(Object id) {
        return super.find(id);
    }

    /**
     * Metoda która zwraca wszystkie zdjęcia powiązanego z danym modelem jachtu.
     *
     * @param yachtModelId identyfikator modelu jachtu
     * @return lista zdjęć
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllImagesByYachtModel")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public List<Image> getAllImagesByYachtModel(Long yachtModelId) throws AppBaseException {
        TypedQuery<Image> typedQuery = entityManager.createNamedQuery("Image.findAllByYachtModel", Image.class);
        typedQuery.setParameter("id", yachtModelId);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            throw AppNotFoundException.yachtModelNotFoundException();
        }
    }

    /**
     * Metoda, która edytuje encje.
     *
     * @param entity modyfikowana encja
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @Override
    @DenyAll
    public void edit(Image entity) throws AppBaseException {
        super.edit(entity);
    }

    /**
     * Metoda, która znajduje wszystkie encje.
     *
     * @return lista encji
     */
    @Override
    @DenyAll
    public List<Image> findAll() {
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
    public void lock(Image entity, LockModeType lockModeType) throws AppBaseException {
        super.lock(entity, lockModeType);
    }
}

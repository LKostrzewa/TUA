package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.EntityNotActiveException;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.ImageFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa menadżera do obsługi operacji związanych ze zdjęciami.
 */
@Stateful(name = "ImageManager")
@Named("ImageManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ImageFacade imageFacade;
    @Inject
    private YachtModelFacade yachtModelFacade;

    /**
     * Metoda, służy do usuwania zdjęć z bazy danych przez menadżera.
     *
     * @param imageId id zdjęcia
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteImage(Long imageId) throws AppBaseException {
        try {
            Image image = imageFacade.find(imageId).orElseThrow(AppNotFoundException::imageNotFoundException);
            yachtModelFacade.lock(image.getYachtModel(), LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            if (!(image.getYachtModel().isActive())) {
                throw EntityNotActiveException.createYachtModelNotActiveException(image.getYachtModel());
            }
            imageFacade.remove(image);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, służy do dodawania nowych zdjęć do bazy danych przez menadżera.
     *
     * @param image zdjęcie w postaci tablicy bajtów
     * @param id    id modelu jachtu dla którego dodajemy zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addImage(byte[] image, Long id) throws AppBaseException {
        try {
            YachtModel yachtModel = yachtModelFacade.find(id).orElseThrow(AppNotFoundException::yachtModelNotFoundException);
            yachtModelFacade.lock(yachtModel, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            if (!yachtModel.isActive()) {
                throw EntityNotActiveException.createYachtModelNotActiveException(yachtModel);
            }
            Image newImage = new Image(image, yachtModel);
            imageFacade.create(newImage);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda służy do wyszukania w bazie wszystkich id zdjęć związanych z danym modelem jachtu.
     *
     * @param yachtModelId id modelu jachtu
     * @return lista wszystkich id zdjęć powiązanych z danym modelem jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Long> getAllImagesByYachtModel(Long yachtModelId) throws AppBaseException {
        List<Image> images = imageFacade.getAllImagesByYachtModel(yachtModelId);
        List<Long> imageIds = new ArrayList<>();
        for (Image i : images) {
            imageIds.add(i.getId());
        }
        return imageIds;
    }

    /**
     * Metoda służy do wyszukania zdjęcia w bazie o odpowiednim id.
     *
     * @param id id zdjęcia
     * @return obiekt encji reprezentujacy zdjecie
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Image getImageById(Long id) throws AppBaseException {
        return imageFacade.find(id).orElseThrow(AppNotFoundException::imageNotFoundException);
    }
}

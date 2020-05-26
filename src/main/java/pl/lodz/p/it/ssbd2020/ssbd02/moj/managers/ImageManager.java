package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.ImageFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private ImageFacade imageFacade;
    @Inject
    private YachtModelFacade yachtModelFacade;

    /**
     * Metoda, służy do usuwania zdjęć z bazy danych przez menadżera
     * @param imageId id zdjęcia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deleteImage")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteImage(Long imageId) throws AppBaseException {
        imageFacade.remove(imageFacade.find(imageId).orElseThrow((AppNotFoundException::imageNotFoundException)));
    }

    /**
     * Metoda, służy do dodawania nowych zdjęć do bazy danych przez menadżera
     * @param image zdjęcie w postaci tablicy bajtów
     * @param id id modelu jachtu dla którego dodajemy zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addImage")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addImage(byte[] image, Long id) throws AppBaseException {
        YachtModel yachtModel = yachtModelFacade.find(id).orElseThrow(AppNotFoundException::yachtModelNotFoundException);
        Image newImage = new Image(image,yachtModel);
        imageFacade.create(newImage);
    }

    /**
     * Metoda służy do wyszukania w bazie wszystkich id zdjęć związanych z danym modelem jachtu
     * @param yachtModelId id modelu jachtu
     * @return lista wszystkich id zdjęć powiązanych z danym modelem jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllImagesByYachtModel")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Long> getAllImagesByYachtModel(Long yachtModelId) throws AppBaseException {
       List<Image> images = imageFacade.getAllImagesByYachtModel(yachtModelId);
       List<Long> imageIds = new ArrayList<>();
       for( Image i: images) {
           imageIds.add(i.getId());
       }
       return imageIds;
    }

    /**
     * Metoda służy do wyszukania zdjęcia w bazie o odpowiednim id
     * @param id id zdjęcia
     * @return obiekt encji reprezentujacy zdjecie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getImageById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Image getImageById(Long id) throws AppBaseException {
        return imageFacade.find(id).orElseThrow(AppNotFoundException::imageNotFoundException);
    }
}

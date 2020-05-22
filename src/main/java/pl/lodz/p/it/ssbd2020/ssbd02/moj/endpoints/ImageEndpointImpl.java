package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.ImageManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.*;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class ImageEndpointImpl implements Serializable, ImageEndpoint {
    @Inject
    private ImageManager imageManager;


    /**
     * Metoda służąca do dodania zdjęcia do szczegółow modelu jachtu
     * @param image zdjęcie w postaci tablicy bajtów
     * @param id id modelu jachtu do którego dodajemy zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addImage")
    public void addImage(byte[] image, Long id) throws AppBaseException {
        imageManager.addImage(image, id);
    }

    /**
     * Metoda służąca do usunięcia zdjęcia ze szczegółów modelu jachtu
     * @param imageId id zdjęcia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deleteImage")
    public void deleteImage(Long imageId) throws AppBaseException{
        imageManager.deleteImage(imageId);
    }


}

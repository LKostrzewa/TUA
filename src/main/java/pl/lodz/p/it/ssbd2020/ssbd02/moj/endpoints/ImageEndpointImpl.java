package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.ImageManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.*;
import java.util.List;

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

    /**
     * Metoda zwracająca id wszystkich zdjęć danego modelu jachtu
     * @param yachtModelId id modelu jachtu
     * @return lista wszystkich id modelu jachtu o podanym id
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllImagesByYachtModel")
    public List<Long> getAllImagesByYachtModel(Long yachtModelId) throws AppBaseException {
        return imageManager.getAllImagesByYachtModel(yachtModelId);
    }

    /**
     * Metoda zwracająca zdjęcie o podanym id
     * @param id id zdjęcia
     * @return obiekt dto reprezentujacy zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getImageById")
    public ImageDto getImageById(Long id) throws AppBaseException {
        Image image = imageManager.getImageById(id);
        return ObjectMapperUtils.map(image, ImageDto.class);
    }
}

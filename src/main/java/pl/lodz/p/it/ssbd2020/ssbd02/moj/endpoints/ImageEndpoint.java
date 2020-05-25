package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;

import java.util.List;


public interface ImageEndpoint {

    /**
     * Metoda służąca do dodania zdjęcia do szczegółow modelu jachtu
     * @param image zdjęcie w postaci tablicy bajtów
     * @param id id modelu jachtu do którego dodajemy zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void addImage(byte[] image, Long id) throws AppBaseException;

    /**
     * Metoda służąca do usunięcia zdjęcia ze szczegółów modelu jachtu
     * @param imageId id zdjęcia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void deleteImage(Long imageId) throws AppBaseException;

    /**
     * Metoda zwracająca id wszystkich zdjęć danego modelu jachtu
     * @param yachtModelId id modelu jachtu
     * @return lista wszystkich id modelu jachtu o podanym id
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    List<Long> getImagesbyYachtModel(Long yachtModelId) throws AppBaseException;

    /**
     * Metoda zwracająca zdjęcie o podanym id
     * @param id id zdjęcia
     * @return obiekt dto reprezentujacy zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    ImageDto getImageById(Long id) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

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

    List<Long> getImagesbyYachtModel(Long yachtModelId) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;

import java.util.List;

/**
 * Interfejs Endpoint do operacji związanych z modelem jachtu.
 */
public interface YachtModelEndpoint {
    /**
     * Metoda, służy do dodawania nowych modeli jachtów do bazy danych przez menadżera.
     *
     * @param newYachtModelDto obiekt DTO z danymi nowego modelu jachty
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void addYachtModel(NewYachtModelDto newYachtModelDto) throws AppBaseException;

    /**
     * Metoda, która zwraca listę wszystkich modeli jachtów.
     *
     * @return lista wszystkich modeli jachtów
     */
    List<ListYachtModelDto> getAllYachtModels();

    /**
     * Metoda, która zwraca szczegóły danego modelu jachtu.
     *
     * @param yachtModelId id danego modelu jachtu
     * @return dto szczegółów danego modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny jeśli operacja zakończy się niepowodzeniem
     */
    YachtModelDetailsDto getYachtModelById(Long yachtModelId) throws AppBaseException;

    /**
     * Metoda która zapisuje wprowadzone przez managera zmiany w modelu jachtu.
     *
     * @param editYachtModelDto obiekt DTO przeznaczony do edycji modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void editYachtModel(EditYachtModelDto editYachtModelDto) throws AppBaseException;

    /**
     * Metoda która deaktywuje model jachtu o podanym id.
     *
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void deactivateYachtModel(Long yachtModelId) throws AppBaseException;

    /**
     * Metoda która zwraca model jachtu do edycji o podanym id.
     *
     * @param yachtModelId id modelu jachtu
     * @return EditYachtModelDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    EditYachtModelDto getEditYachtModelDtoById(Long yachtModelId) throws AppBaseException;
}

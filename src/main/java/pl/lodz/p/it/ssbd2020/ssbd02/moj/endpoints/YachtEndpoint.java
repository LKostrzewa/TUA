package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtListDto;

import java.util.List;

/**
 * Interfejs Endpoint do operacji związanych z jachtem.
 */
public interface YachtEndpoint {
    /**
     * Metoda, służy do dodawania nowych jachtów do bazy danych przez administratora.
     *
     * @param newYachtDto obiekt DTO z danymi nowego jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void addYacht(NewYachtDto newYachtDto) throws AppBaseException;

    /**
     * Metoda, która zwraca wszystkie jachty.
     *
     * @return lista jachtów
     */
    List<YachtListDto> getAllYachts();

    /**
     * Metoda, która zwraca yacht o podanym id.
     *
     * @param yachtId id jachtu.
     * @return YachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    YachtDto getYachtById(Long yachtId) throws AppBaseException;

    /**
     * Metoda, która zwraca yacht do edycji o podanym id.
     *
     * @param yachtId id jachtu.
     * @return EditYachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    EditYachtDto getEditYachtDtoById(Long yachtId) throws AppBaseException;

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w jachcie.
     *
     * @param editYachtDto id jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void editYacht(EditYachtDto editYachtDto) throws AppBaseException;

    /**
     * Metoda, która deaktywuje jacht o podanym id.
     *
     * @param yachtId id jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void deactivateYacht(Long yachtId) throws AppBaseException;
}

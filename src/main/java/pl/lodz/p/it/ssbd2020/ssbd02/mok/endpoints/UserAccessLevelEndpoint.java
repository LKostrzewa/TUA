package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;

/**
 * Interfejs Endpoint do operacji łączenia użytkowników z poziomami dostępu.
 */
public interface UserAccessLevelEndpoint {
    /**
     * Metoda, która zwraca obiekt UserAccessLevelDto zawierający informacje o poziomach dostępu danego użytkownika.
     *
     * @param userId identyfikator użytkownika.
     * @return obiekt klasy UserAccessLevelDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    UserAccessLevelDto findUserAccessLevelById(Long userId) throws AppBaseException;

    /**
     * Metoda, która zwraca obiekt UserAccessLevelDto zawierający informacje o poziomach dostępu danego użytkownika.
     *
     * @return obiekt klasy UserAccessLevelDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    UserAccessLevelDto findUserAccessLevelByLogin() throws AppBaseException;

    /**
     * Metoda, która modyfikuje przypisane do użytkownika poziomy dostępu.
     *
     * @param userAccessLevelDto obiekt zawierająy informacje o obecnych i pożądanych poziomach dotępu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void editUserAccessLevels(UserAccessLevelDto userAccessLevelDto) throws AppBaseException;
}

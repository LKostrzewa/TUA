package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Interfejs Endpoint do operacji związanych z jachtami oraz portami.
 */
public interface YachtPortEndpoint {
    /**
     * Metoda pobierająca wszystki jachty przypisane do danego portu.
     *
     * @param portId identyfikator danego portu
     * @return lista YachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    List<YachtDto> getAllYachtsByPort(Long portId) throws AppBaseException;

    /**
     * Metoda przypisująca jacht do portu.
     *
     * @param portId identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void assignYachtToPort(Long portId, Long yachtId) throws AppBaseException;

    /**
     * Metoda odpisująca jacht z portu.
     *
     * @param portId identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void retractYachtFromPort(Long portId, Long yachtId) throws AppBaseException;
}

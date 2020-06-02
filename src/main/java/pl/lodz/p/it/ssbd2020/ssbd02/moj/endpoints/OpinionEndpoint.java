package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDto;

import java.util.List;

/**
 * Interfejs Endpoint do operacji związanych z opinią.
 */
public interface OpinionEndpoint {

    /**
     * Metoda, która dodaje nową opinię.
     *
     * @param newOpinionDto obiekt DTO z danymi nowej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void addOpinion(NewOpinionDto newOpinionDto) throws AppBaseException;

    /**
     * Metoda pobierająca wszystkie opinie przypisane do danego jachtu.
     *
     * @param yachtId identyfikator jachtu
     * @return lista opini dla danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    List<OpinionDto> getAllOpinionsByYacht(Long yachtId) throws AppBaseException;

    /**
     * Metoda zwracająca opinię do edycji na podstawie przekazanego identyfikatora.
     *
     * @param opinionId identyfikator opinii
     * @return opinia do edycji
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    EditOpinionDto getOpinionById(Long opinionId) throws AppBaseException;

    /**
     * Metoda służąca do zapisu nowej wersji opinii.
     *
     * @param editOpinionDto obiekt DTO z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void editOpinion(EditOpinionDto editOpinionDto) throws AppBaseException;
}

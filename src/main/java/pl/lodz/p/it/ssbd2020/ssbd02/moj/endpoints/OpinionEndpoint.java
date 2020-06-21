package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;

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
    void addOpinion(NewOpinionDto newOpinionDto, String rentalBusinessKey) throws AppBaseException;

    /**
     * Metoda zwracająca opinię do edycji na podstawie przekazanego klucza biznesowego.
     *
     * @param businessKey klucz biznesowy opinii
     * @return opinia do edycji
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    EditOpinionDto getOpinionByRentalBusinessKey(String businessKey) throws AppBaseException;

    /**
     * Metoda służąca do zapisu nowej wersji opinii.
     *
     * @param editOpinionDto obiekt DTO z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void editOpinion(EditOpinionDto editOpinionDto) throws AppBaseException;
}

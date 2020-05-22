package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface YachtModelEndpoint {
    /**
     * Metoda, służy do dodawania nowych modeli jachtów do bazy danych przez menadżera
     *
     * @param newYachtModelDto obiekt DTO z danymi nowego modelu jachty.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void addYachtModel(NewYachtModelDto newYachtModelDto) throws AppBaseException;

    /**
     * Metoda, która zwraca listę wszystkich modeli jachtów
     * @return lista wszystkich modeli jachtów
     */
    List<ListYachtModelDto> getAllYachtModels();


    /**
     * Metoda, która zwraca szczegóły danego modelu jachtu
     * @param yachtModelId id danego modelu jachtu
     * @return dto szczegółów danego modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny jeśli operacja zakończy się niepowodzeniem
     */
    YachtModelDetailsDto getYachtModelById(Long yachtModelId) throws AppBaseException;
    void editYachtModel(Long yachtModelId, EditYachtModelDto editYachtModelDto) throws AppBaseException;
    void deactivateYachtModel(Long yachtModelId) throws AppBaseException;


}

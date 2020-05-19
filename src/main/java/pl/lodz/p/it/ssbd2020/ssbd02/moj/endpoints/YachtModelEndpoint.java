package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;

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
    List<ListYachtModelDto> getAllYachtModels();
    ListYachtModelDto getYachtModelById(Long yachtModelId) throws AppBaseException;
    void editYachtModel(Long yachtModelId, EditYachtModelDto editYachtModelDto) throws AppBaseException;
    void deactivateYachtModel(Long yachtModelId) throws AppBaseException;


}

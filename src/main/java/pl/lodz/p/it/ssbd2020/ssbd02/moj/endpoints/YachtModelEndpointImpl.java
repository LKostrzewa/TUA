package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtModelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class YachtModelEndpointImpl implements Serializable, YachtModelEndpoint {
    @Inject
    private YachtModelManager yachtModelManager;

    /**
     * Metoda, służy do dodawania nowych modeli jachtów do bazy danych przez menadżera
     *
     * @param newYachtModelDto obiekt DTO z danymi nowego modelu jachty.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addYachtModel")
    public void addYachtModel(NewYachtModelDto newYachtModelDto) throws AppBaseException {
        YachtModel yachtModel = ObjectMapperUtils.map(newYachtModelDto, YachtModel.class);
        yachtModelManager.addYachtModel(yachtModel);
    }

    /**
     * Metoda, która zwraca listę wszystkich modeli jachtów
     * @return lista wszystkich modeli jachtów
     */
    @RolesAllowed("getAllYachtModels")
    public List<ListYachtModelDto> getAllYachtModels() {
        return ObjectMapperUtils.mapAll(yachtModelManager.getAllYachtModels(), ListYachtModelDto.class);
    }

    /**
     * Metoda, która zwraca szczegóły danego modelu jachtu
     * @param yachtModelId id danego modelu jachtu
     * @return dto szczegółów danego modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getYachtModelById")
    public YachtModelDetailsDto getYachtModelById(Long yachtModelId) throws AppBaseException{
        YachtModel yachtModel = yachtModelManager.getYachtModelById(yachtModelId);
        return ObjectMapperUtils.map(yachtModel, YachtModelDetailsDto.class);
    }

    public void editYachtModel(Long yachtModelId, EditYachtModelDto editYachtModelDto) throws AppBaseException {
        YachtModel yachtModelToEdit = ObjectMapperUtils.map(editYachtModelDto, YachtModel.class);
        yachtModelManager.editYachtModel(yachtModelId, yachtModelToEdit);
    }

    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        yachtModelManager.deactivateYachtModel(yachtModelId);
    }
}

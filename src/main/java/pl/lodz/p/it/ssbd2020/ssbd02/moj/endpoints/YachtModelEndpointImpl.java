package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
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

    private YachtModel yachtModelEditEntity;

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

    /**
     * Metoda która zapisuje wprowadzone przez managera zmiany w modelu jachtu
     * @param editYachtModelDto obiekt DTO przeznaczony do edycji modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editYachtModel")
    public void editYachtModel(EditYachtModelDto editYachtModelDto) throws AppBaseException {
        yachtModelEditEntity.setModel(editYachtModelDto.getModel());
        yachtModelEditEntity.setManufacturer(editYachtModelDto.getManufacturer());
        yachtModelEditEntity.setBasicPrice(editYachtModelDto.getBasicPrice());
        yachtModelEditEntity.setCapacity(editYachtModelDto.getCapacity());
        yachtModelEditEntity.setGeneralDescription(editYachtModelDto.getGeneralDescription());
        yachtModelManager.editYachtModel(this.yachtModelEditEntity);
    }

    /**
     * Metoda która deaktywuje model jachtu o podanym ID
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivateYachtModel")
    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        yachtModelManager.deactivateYachtModel(yachtModelId);
    }

    /**
     * Metoda która zwraca model jachtu do edycji o podanym id
     * @param yachtModelId id modelu jachtu
     * @return EditYachtModelDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getEditYachtModelDtoById")
    public EditYachtModelDto getEditYachtModelDtoById(Long yachtModelId) throws AppBaseException {
        this.yachtModelEditEntity = yachtModelManager.getYachtModelById(yachtModelId);
        return ObjectMapperUtils.map(this.yachtModelEditEntity, EditYachtModelDto.class);
    }
}

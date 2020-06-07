package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtListDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacja interfejsu YachtEndpoint.
 */
@Stateful
@Interceptors(LoggerInterceptor.class)
public class YachtEndpointImpl implements Serializable, YachtEndpoint {
    @Inject
    private YachtManager yachtManager;

    private Yacht yachtEditEntity;

    /**
     * Metoda, służy do dodawania nowych jachtów do bazy danych przez menadżera.
     *
     * @param newYachtDto obiekt DTO z danymi nowego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addYacht")
    public void addYacht(NewYachtDto newYachtDto) throws AppBaseException {
        // TODO nie moge objectMapperem zmapować, bo production year nie ma metody set
        Yacht yacht = new Yacht(newYachtDto.getName(), newYachtDto.getProductionYear(), newYachtDto.getPriceMultiplier(), newYachtDto.getEquipment(), null);
        yachtManager.addYacht(yacht, newYachtDto.getYachtModelId());
    }

    /**
     * Metoda, która zwraca wszystkie jachty.
     *
     * @return lista jachtów
     */
    @RolesAllowed("getAllYachts")
    public List<YachtListDto> getAllYachts() {
        List<Yacht> yachtList = yachtManager.getAllYachts();
        List<YachtListDto> yachtListDtoList = new ArrayList<>();
        for (Yacht yacht: yachtList) {
            yachtListDtoList.add(new YachtListDto(yacht.getId(),yacht.getName(),yacht.getYachtModel().getModel(),
                    (yacht.getCurrentPort() != null) ? yacht.getCurrentPort().getName() : null,
                    (yacht.getAvgRating() != null) ? yacht.getAvgRating().floatValue() : null
                    ,yacht.isActive()));
        }
       return yachtListDtoList;
    }

    /**
     * Metoda, która zwraca yacht o podanym id.
     *
     * @param yachtId id jachtu.
     * @return YachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getYachtById")
    public YachtDto getYachtById(Long yachtId) throws AppBaseException {
        Yacht yacht = yachtManager.getYachtById(yachtId);
        return ObjectMapperUtils.map(yacht, YachtDto.class);
    }

    /**
     * Metoda, która zwraca yacht do edycji o podanym id.
     *
     * @param yachtId id jachtu.
     * @return EditYachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getEditYachtDtoById")
    public EditYachtDto getEditYachtDtoById(Long yachtId) throws AppBaseException {
        this.yachtEditEntity = yachtManager.getYachtById(yachtId);
        return ObjectMapperUtils.map(this.yachtEditEntity, EditYachtDto.class);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w jachcie.
     *
     * @param editYachtDto id jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editYacht")
    public void editYacht(EditYachtDto editYachtDto) throws AppBaseException {
        yachtEditEntity.setName(editYachtDto.getName());
        yachtEditEntity.setPriceMultiplier(editYachtDto.getPriceMultiplier());
        yachtEditEntity.setEquipment(editYachtDto.getEquipment());
        yachtManager.editYacht(this.yachtEditEntity);
    }

    /**
     * Metoda, która deaktywuje jacht o podanym id.
     *
     * @param yachtId id jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivateYacht")
    public void deactivateYacht(Long yachtId) throws AppBaseException {
        yachtManager.deactivateYacht(yachtId);
    }
}

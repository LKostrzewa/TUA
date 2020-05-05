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

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtEndpoint implements Serializable {
    @Inject
    private YachtManager yachtManager;

    public void addYacht(NewYachtDto newYachtDto) {
        Yacht yacht = ObjectMapperUtils.map(newYachtDto, Yacht.class);
        yachtManager.addYacht(yacht);
    }

    public List<YachtListDto> getAllYachts() {
        return ObjectMapperUtils.mapAll(yachtManager.getAllYachts(), YachtListDto.class);
    }

    public YachtDto getYachtById(Long yachtId) {
        Yacht yacht = yachtManager.getYachtById(yachtId);
        return ObjectMapperUtils.map(yacht, YachtDto.class);
    }

    public void editYacht(Long yachtId, EditYachtDto editYachtDto) throws AppBaseException {
        Yacht yachtToEdit = ObjectMapperUtils.map(editYachtDto, Yacht.class);
        yachtManager.editYacht(yachtId, yachtToEdit);
    }

    public void deactivateYacht(Long yachtId) throws AppBaseException {
        yachtManager.deactivateYacht(yachtId);
    }
}

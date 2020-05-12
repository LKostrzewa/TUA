package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtModelManager;
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
public class YachtModelEndpoint implements Serializable {
    @Inject
    private YachtModelManager yachtModelManager;

    public void addYachtModel(NewYachtModelDto newYachtModelDTO) {
        YachtModel yachtModel = ObjectMapperUtils.map(newYachtModelDTO, YachtModel.class);
        yachtModelManager.addYachtModel(yachtModel);
    }

    public List<ListYachtModelDto> getAllYachtModels() {
        return ObjectMapperUtils.mapAll(yachtModelManager.getAllYachtModels(), ListYachtModelDto.class);
    }

    public ListYachtModelDto getYachtModelById(Long yachtModelId) throws AppBaseException{
        YachtModel yachtModel = yachtModelManager.getYachtModelById(yachtModelId);
        return ObjectMapperUtils.map(yachtModel, ListYachtModelDto.class);
    }

    public void editYachtModel(Long yachtModelId, EditYachtModelDto editYachtModelDto) throws AppBaseException {
        YachtModel yachtModelToEdit = ObjectMapperUtils.map(editYachtModelDto, YachtModel.class);
        yachtModelManager.editYachtModel(yachtModelId, yachtModelToEdit);
    }

    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        yachtModelManager.deactivateYachtModel(yachtModelId);
    }
}

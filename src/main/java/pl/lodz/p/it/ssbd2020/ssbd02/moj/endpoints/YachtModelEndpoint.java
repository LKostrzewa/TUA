package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.UpdateYachtModelDTO;
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

    public void addYachtModel(NewYachtModelDTO newYachtModelDTO) {
        YachtModel yachtModel = ObjectMapperUtils.map(newYachtModelDTO, YachtModel.class);
        yachtModelManager.addYachtModel(yachtModel);
    }

    public List<ListYachtModelDTO> getAllYachtModels() {
        return ObjectMapperUtils.mapAll(yachtModelManager.getAllYachtModels(), ListYachtModelDTO.class);
    }

    public ListYachtModelDTO getYachtModelById(Long yachtModelId) {
        YachtModel yachtModel = yachtModelManager.getYachtModelById(yachtModelId);
        return ObjectMapperUtils.map(yachtModel, ListYachtModelDTO.class);
    }

    public void updateYachtModel(Long yachtModelId, UpdateYachtModelDTO updateYachtModelDTO) {
        YachtModel yachtModelToUpdate = ObjectMapperUtils.map(updateYachtModelDTO, YachtModel.class);
        yachtModelManager.updateYachtModel(yachtModelId, yachtModelToUpdate);
    }

    public void deactivateYachtModel(Long yachtModelId) {
        yachtModelManager.deactivateYachtModel(yachtModelId);
    }
}

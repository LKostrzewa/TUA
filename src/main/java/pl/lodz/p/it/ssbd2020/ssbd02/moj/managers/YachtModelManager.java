package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtModelManager {
    @Inject
    private YachtModelFacade yachtModelFacade;

    public void addYachtModel(YachtModel yachtModel) {
        yachtModelFacade.create(yachtModel);
    }

    public List<YachtModel> getAllYachtModels() {
        return yachtModelFacade.findAll();
    }

    public YachtModel getYachtModelById(Long yachtModelId) {
        return yachtModelFacade.find(yachtModelId);
    }

    public void editYachtModel(Long yachtModelId, YachtModel yachtModelToEdit) {
        yachtModelToEdit.setId(yachtModelId);
        yachtModelFacade.edit(yachtModelToEdit);
    }

    public void deactivateYachtModel(Long yachtModelId) {
        YachtModel yachtModelToDeactivate = getYachtModelById(yachtModelId);
        yachtModelToDeactivate.setActive(false);
        yachtModelFacade.edit(yachtModelToDeactivate);
    }
}

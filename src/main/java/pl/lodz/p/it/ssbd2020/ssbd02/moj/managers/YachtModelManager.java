package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
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

    public void addYachtModel(YachtModel yachtModel) throws AppBaseException {
        yachtModelFacade.create(yachtModel);
    }

    public List<YachtModel> getAllYachtModels() {
        return yachtModelFacade.findAll();
    }

    public YachtModel getYachtModelById(Long yachtModelId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return yachtModelFacade.find(yachtModelId).orElseThrow(() -> new AppBaseException("nie ma tego modelu"));
    }

    public void editYachtModel(Long yachtModelId, YachtModel yachtModelToEdit) throws AppBaseException {
      //  yachtModelToEdit.setId(yachtModelId);
        yachtModelFacade.edit(yachtModelToEdit);
    }

    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException{
        YachtModel yachtModelToDeactivate = getYachtModelById(yachtModelId);
        yachtModelToDeactivate.setActive(false);
        yachtModelFacade.edit(yachtModelToDeactivate);
    }
}

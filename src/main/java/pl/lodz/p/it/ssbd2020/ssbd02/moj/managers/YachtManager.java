package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
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
public class YachtManager {
    @Inject
    private YachtFacade yachtFacade;
    @Inject
    private YachtModelFacade yachtModelFacade;

    public void addYacht(Yacht yacht) {
        yachtFacade.create(yacht);
    }

    public List<Yacht> getAllYachts() {
        return yachtFacade.findAll();
    }

    public Yacht getYachtById(Long yachtId) {
        return yachtFacade.find(yachtId);
    }

    public void editYacht(Long yachtId, Yacht yachtToEdit) {
        //yachtToEdit.setId(yachtId);
        yachtFacade.edit(yachtToEdit);
    }

    public void deactivateYacht(Long yachtId) {
        Yacht yachtToDeactivate = getYachtById(yachtId);
        yachtToDeactivate.setActive(false);
        yachtFacade.edit(yachtToDeactivate);
    }
}

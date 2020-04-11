package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.YachtFacade;
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

    public void addYacht(Yacht yacht){
        yachtFacade.create(yacht);
    }
    public List<Yacht> getAllYachts(){
        yachtFacade.findAll();
    }
    public void getYachtById(Long yachtId){
        yachtFacade.find(yachtId);
    }
    public void updateYacht(Long yachtId, Yacht yacht){
        yachtFacade.edit(yacht);
    }
    public void deactivateYacht(Long yachtId){
        yachtFacade.edit(yacht);
    }
}

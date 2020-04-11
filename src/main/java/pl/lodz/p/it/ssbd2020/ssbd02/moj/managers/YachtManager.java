package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtManager {

    @Inject
    private YachtFacade yachtFacade;

    public void addYacht(Yacht yacht){

    }
    public void getAllYachts(){

    }
    public void getYachtById(Long yachtId){

    }
    public void updateYacht(Long yachtId, Yacht yacht){

    }
    public void deactivateYacht(Long yachtId){

    }
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import org.modelmapper.ModelMapper;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtEndpoint {

    @Inject
    private YachtManager yachtManager;

    public void addYacht(NewYachtDto newYachtDto){
        ModelMapper modelMapper = new ModelMapper();
        Yacht yacht = modelMapper.map(newYachtDto, Yacht.class);
        yachtManager.addYacht(yacht);

    }
    public void getAllYachts(){

    }
    public void getYachtById(Long yachtId){

    }
    public void updateYacht(Long yachtId, YachtDto yachtDto){

    }
    public void deactivateYacht(Long yachtId){

    }

}

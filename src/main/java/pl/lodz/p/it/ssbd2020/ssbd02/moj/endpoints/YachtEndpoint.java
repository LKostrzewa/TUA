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
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtEndpoint {

    @Inject
    private ModelMaper modelMaper;
    @Inject
    private YachtManager yachtManager;

    public void addYacht(NewYachtDto newYachtDto){
        Yacht yacht = modelMapper.map(newYachtDto, Yacht.class);
        yachtManager.addYacht(yacht);

    }
    public List<YachtDto> getAllYachts(){
        return yachtManager.getAllYachts().stream().map(n -> modelMapper.map(n, YachtDto.class));
    }
    public void getYachtById(Long yachtId){
        yachtManager.getYachtById(yachtId);
    }
    public void updateYacht(Long yachtId, YachtDto yachtDto){
        Yacht yacht = modelMapper.map(yachtDto, Yacht.class);
        yachtManager.updateYacht(yachtId,yachtDto);
    }
    public void deactivateYacht(Long yachtId){
        yachtManager.deactivateYacht(yachtId);

    }

}

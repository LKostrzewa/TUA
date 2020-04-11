package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;


import org.modelmapper.ModelMapper;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.UpdateYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtEndpoint {

    @Inject
    private ModelMapper modelMapper;
    @Inject
    private YachtManager yachtManager;

    public void addYacht(NewYachtDto newYachtDto){
        Yacht yacht = modelMapper.map(newYachtDto, Yacht.class);
        yachtManager.addYacht(yacht);

    }
    public List<YachtDto> getAllYachts(){
        return yachtManager.getAllYachts().stream().map(n -> modelMapper.map(n, YachtDto.class)).collect(Collectors.toList());
    }
    public YachtDto getYachtById(Long yachtId){
        Yacht yacht = yachtManager.getYachtById(yachtId);
        return modelMapper.map(yacht, YachtDto.class);
    }
    public void updateYacht(Long yachtId, UpdateYachtDto updateYachtDto){
        Yacht yachtToUpdate = modelMapper.map(updateYachtDto, Yacht.class);
        yachtManager.updateYacht(yachtId,yachtToUpdate);
    }
    public void deactivateYacht(Long yachtId){
        yachtManager.deactivateYacht(yachtId);

    }

}

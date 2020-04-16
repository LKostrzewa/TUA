package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtPortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ModelMapper;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtPortEndpoint {

    @Inject
    private ModelMapper modelMapper;
    @Inject
    private YachtPortManager yachtPortManager;

    public List<YachtDto> getAllYachtsByPort(Long portId){
        return yachtPortManager.getAllYachtsByPort(portId).stream().map(n -> modelMapper.map(n, YachtDto.class)).collect(Collectors.toList());
    }

    public void assignYachtToPort(Long portId, Long yachtId){
        yachtPortManager.assignYachtToPort(portId, yachtId);
    }

    public void retractYachtFromPort(Long portId, Long yachtId){
        yachtPortManager.retractYachtFromPort(portId, yachtId);
    }
}

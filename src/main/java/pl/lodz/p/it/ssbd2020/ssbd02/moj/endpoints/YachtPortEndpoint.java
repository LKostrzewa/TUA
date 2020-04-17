package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtPortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtPortEndpoint {
    @Inject
    private YachtPortManager yachtPortManager;

    public List<YachtDto> getAllYachtsByPort(Long portId) {
        return ObjectMapperUtils.mapAll(yachtPortManager.getAllYachtsByPort(portId), YachtDto.class);
    }

    public void assignYachtToPort(Long portId, Long yachtId) {
        yachtPortManager.assignYachtToPort(portId, yachtId);
    }

    public void retractYachtFromPort(Long portId, Long yachtId) {
        yachtPortManager.retractYachtFromPort(portId, yachtId);
    }
}

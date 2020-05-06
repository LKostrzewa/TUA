package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtPortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtPortEndpoint implements Serializable {
    @Inject
    private YachtPortManager yachtPortManager;

    public List<YachtDto> getAllYachtsByPort(Long portId) {
        return ObjectMapperUtils.mapAll(yachtPortManager.getAllYachtsByPort(portId), YachtDto.class);
    }

    public void assignYachtToPort(Long portId, Long yachtId) throws AppBaseException {
        yachtPortManager.assignYachtToPort(portId, yachtId);
    }

    public void retractYachtFromPort(Long portId, Long yachtId) throws AppBaseException {
        yachtPortManager.retractYachtFromPort(portId, yachtId);
    }
}

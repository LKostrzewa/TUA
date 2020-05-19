package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.PortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtPortManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class YachtPortEndpointImpl implements Serializable, YachtPortEndpoint {
    @Inject
    private YachtPortManager yachtPortManager;

    /**
     * Metoda pobierająca wszystki jachty przypisane do danego portu
     *
     * @param portId identyfikator danego portu
     * @return lista YachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllYachtsByPort")
    public List<YachtDto> getAllYachtsByPort(Long portId) throws AppBaseException {
        return ObjectMapperUtils.mapAll(yachtPortManager.getAllYachtsByPort(portId), YachtDto.class);
    }

    /**
     * Metoda przypisująca jacht do portu
     *
     * @param portId identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("assignYachtToPort")
    public void assignYachtToPort(Long portId, Long yachtId) throws AppBaseException {
        yachtPortManager.assignYachtToPort(portId, yachtId);
    }

    /**
     * Metoda odpisująca jacht z portu
     *
     * @param portId identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("retractYachtToPort")
    public void retractYachtFromPort(Long portId, Long yachtId) throws AppBaseException {
        yachtPortManager.retractYachtFromPort(portId, yachtId);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Collection;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtPortManager {
    @Inject
    private PortFacade portFacade;
    @Inject
    private YachtFacade yachtFacade;

    public List<Yacht> getAllYachtsByPort(Long portId) {
        Port port = portFacade.find(portId);
        return (List<Yacht>) port.getYachts();
    }

    public void assignYachtToPort(Long portId, Long yachtId) {
        Port port = portFacade.find(portId);
        Yacht yacht = yachtFacade.find(yachtId);
        yacht.setCurrentPort(port);
        Collection<Yacht> yachts = port.getYachts();
        yachts.add(yacht);
        port.setYachts(yachts);
        portFacade.edit(port);
    }

    public void retractYachtFromPort(Long portId, Long yachtId) {
        Port port = portFacade.find(portId);
        Yacht yacht = yachtFacade.find(yachtId);
        yacht.setCurrentPort(null);
        Collection<Yacht> yachts = port.getYachts();
        yachts.remove(yacht);
        port.setYachts(yachts);
        portFacade.edit(port);
    }
}

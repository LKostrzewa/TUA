package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class PortManager {
    @Inject
    private PortFacade portFacade;

    public void addPort(Port port) {
        portFacade.create(port);
    }

    public List<Port> getAllPorts() {
        return portFacade.findAll();
    }

    public Port getPortById(Long portId) {
        return portFacade.find(portId);
    }

    public void editPort(Long portId, Port portToEdit) {
        //portToEdit.setId(portId);
        portFacade.edit(portToEdit);
    }

    public void deactivatePort(Long portId) {
        Port portToDeactivate = getPortById(portId);
        portToDeactivate.setActive(false);
        portFacade.edit(portToDeactivate);
    }
}

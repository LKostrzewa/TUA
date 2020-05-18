package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.EntityNotActiveException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.YachtReservedException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

    /**
     * Metoda pobierająca wszystkie jachty przypisane do danego portu
     *
     * @param portId identyfikator danego portu
     * @return lista Yacht
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllYachtsByPort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Yacht> getAllYachtsByPort(Long portId) throws AppBaseException {
        Port port = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        return (List<Yacht>) port.getYachts();
    }

    /**
     * Metoda przypisująca jacht do portu
     *
     * @param portId identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("assignYachtToPort")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void assignYachtToPort(Long portId, Long yachtId) throws AppBaseException {
        Yacht yacht = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        if(!yacht.isActive()){
            throw EntityNotActiveException.createYachtNotActiveException(yacht);
        }
        Port port = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        if(!port.isActive()){
            throw EntityNotActiveException.createPortNotActiveException(port);
        }
        yacht.setCurrentPort(port);
        port.getYachts().add(yacht);
        /*Collection<Yacht> yachts = port.getYachts();
        yachts.add(yacht);
        port.setYachts(yachts);*/
        portFacade.edit(port);
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
        Yacht yacht = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        //TODO pobierać statusy rezerwacji z properties
        if(yacht.getRentals().stream().anyMatch(r -> r.getRentalStatus().getName().equals("STARTED"))){
            throw YachtReservedException.createYachtReservedException(yacht);
        }
        Port port = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        yacht.setCurrentPort(null);
        port.getYachts().remove(yacht);
        /*Collection<Yacht> yachts = port.getYachts();
        yachts.remove(yacht);
        port.setYachts(yachts);*/
        portFacade.edit(port);
    }
}

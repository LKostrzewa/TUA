package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Port;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.PortFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.LockModeType;
import java.util.List;

/**
 * Klasa menadżera do obsługi operacji związanych z jachtami i portami.
 */
@Stateful(name = "YachtPortManager")
@Named("YachtPortManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtPortManager extends AbstractManager implements SessionSynchronization {
    private final PropertyReader propertyReader = new PropertyReader();
    @Inject
    private PortFacade portFacade;
    @Inject
    private YachtFacade yachtFacade;
    private String RENTAL_PENDING_STATUS;
    private String RENTAL_STARTED_STATUS;

    @PostConstruct
    public void init() {
        RENTAL_PENDING_STATUS = propertyReader.getProperty("config", "PENDING_STATUS");
        RENTAL_STARTED_STATUS = propertyReader.getProperty("config", "STARTED_STATUS");
    }

    /**
     * Metoda pobierająca wszystkie jachty przypisane do danego portu.
     *
     * @param portId identyfikator danego portu
     * @return lista Yacht
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Yacht> getAllYachtsByPort(Long portId) throws AppBaseException {
        Port port = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
        return (List<Yacht>) port.getYachts();
    }

    /**
     * Metoda przypisująca jacht do portu.
     *
     * @param portId  identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void assignYachtToPort(Long portId, Long yachtId) throws AppBaseException {
        try {
            Yacht yacht = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createPortNotFoundException);
            Port port = portFacade.find(portId).orElseThrow(AppNotFoundException::createPortNotFoundException);
            yachtFacade.lock(yacht, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
            portFacade.lock(port, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

            if (!yacht.isActive()) {
                throw EntityNotActiveException.createYachtNotActiveException(yacht);
            }
            if (yacht.getCurrentPort() != null) {
                throw YachtPortChangedException.createYachtAssignedException(yacht);
            }

            if (!port.isActive()) {
                throw EntityNotActiveException.createPortNotActiveException(port);
            }
            yacht.setCurrentPort(port);
            yachtFacade.edit(yacht);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda odpisująca jacht z portu.
     *
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void retractYachtFromPort(Long yachtId) throws AppBaseException {
        try {
            Yacht yacht = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createPortNotFoundException);
            if (yacht.getRentals().stream().anyMatch(r -> r.getRentalStatus().getName().equals(RENTAL_PENDING_STATUS)
                    || r.getRentalStatus().getName().equals(RENTAL_STARTED_STATUS))) {
                throw YachtReservedException.createYachtReservedException(yacht);
            }
            if (yacht.getCurrentPort() == null) {
                throw YachtPortChangedException.createYachtNotAssignedException(yacht);
            }
            yacht.setCurrentPort(null);
            yachtFacade.edit(yacht);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }
}

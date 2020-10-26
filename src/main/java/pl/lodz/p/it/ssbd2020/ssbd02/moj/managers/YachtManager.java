package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.LockModeType;
import java.util.List;

/**
 * Klasa menadżera do obsługi operacji związanych z jachtami.
 */
@Stateful(name = "YachtManager")
@Named("YachtManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private YachtFacade yachtFacade;
    @Inject
    private YachtModelFacade yachtModelFacade;

    /**
     * Metoda, służy do dodawania nowych jachtów do bazy danych przez menadżera.
     *
     * @param yacht        obiekt jacht z danymi nowego jachtu
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addYacht(Yacht yacht, Long yachtModelId) throws AppBaseException {
        try {
            YachtModel yachtModel = yachtModelFacade.find(yachtModelId).orElseThrow(AppNotFoundException::yachtModelNotFoundException);
            Yacht newYacht = new Yacht(yacht.getName(), yacht.getProductionYear(), yacht.getPriceMultiplier(), yacht.getEquipment(), yachtModel);

            yachtModelFacade.lock(yachtModel, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

            if (!yachtModel.isActive()) {
                throw EntityNotActiveException.createYachtModelNotActiveException(yachtModel);
            }

            if (yachtFacade.existByName(newYacht.getName())) {
                throw ValueNotUniqueException.createYachtNameNotUniqueException(newYacht);
            }

            yachtFacade.create(newYacht);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która zwraca wszystkie jachty
     *
     * @return lista jachtów
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Yacht> getAllYachts() {
        return yachtFacade.findAll();
    }

    /**
     * Metoda, która zwraca yacht o podanym id.
     *
     * @param yachtId id jachtu
     * @return yacht dto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Yacht getYachtById(Long yachtId) throws AppBaseException {
        return yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w jachcie.
     *
     * @param yachtToEdit edytowany jacht
     * @param nameChanged boolean informujący o tym czy podczas edycji została zmieniona nazwa jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editYacht(Yacht yachtToEdit, boolean nameChanged) throws AppBaseException {
        try {
            if (!yachtToEdit.isActive()) {
                throw EntityNotActiveException.createYachtNotActiveException(yachtToEdit);
            }
            if (nameChanged && yachtFacade.existByName(yachtToEdit.getName())) {
                throw ValueNotUniqueException.createYachtNameNotUniqueException(yachtToEdit);
            }
            yachtFacade.edit(yachtToEdit);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która deaktywuje jacht o podanym id.
     *
     * @param yachtId id jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"MANAGER"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deactivateYacht(Long yachtId) throws AppBaseException {
        try {
            Yacht yachtToDeactivate = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
            yachtToDeactivate.setActive(false);
            yachtFacade.edit(yachtToDeactivate);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }
}
